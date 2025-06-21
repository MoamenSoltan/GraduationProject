package org.example.backend.service;

import org.example.backend.dto.QuizDTO.*;
import org.example.backend.entity.*;
import org.example.backend.enums.QuestionType;
import org.example.backend.exception.QuizAlreadySubmittedException;
import org.example.backend.mapper.QuizMapper;
import org.example.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizSubmissionService {
    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final CourseRepository courseRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizMapper quizMapper=new QuizMapper();
    private final FileService fileService;

    public QuizSubmissionService(QuizRepository quizRepository, StudentRepository studentRepository, StudentCourseRepository studentCourseRepository, QuizSubmissionRepository quizSubmissionRepository, StudentAnswerRepository studentAnswerRepository, CourseRepository courseRepository, QuizQuestionRepository quizQuestionRepository, FileService fileService) {
        this.quizRepository = quizRepository;
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.studentAnswerRepository = studentAnswerRepository;
        this.courseRepository = courseRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.fileService = fileService;
    }

    @Transactional
    public void submitQuiz(List<AnswerDTO> dtos, Long quizId, Long courseId,String studentEmail) {
        Student student=studentRepository.findStudentByEmail(studentEmail)
                .orElseThrow(()->new RuntimeException("Student not found"));


        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isEnrolled=studentCourseRepository.isStudentEnrolledInCourse(student,course);
        if(!isEnrolled)
        {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        Quiz quiz=quizRepository.findQuizByCourseAndQuizId(courseId,quizId)
                .orElseThrow(()->new RuntimeException("Quiz not found"));

        Optional<QuizSubmission> existingSubmissionOpt = quizSubmissionRepository.findByQuizAndStudent(quiz, student);
        if (existingSubmissionOpt.isPresent()) {
            QuizSubmission existingSubmission = existingSubmissionOpt.get();
            throw new QuizAlreadySubmittedException(existingSubmission.getScore());
        }

        QuizSubmission quizSubmission=new QuizSubmission();
        quizSubmission.setQuiz(quiz);
        quizSubmission.setStudent(student);
        quizSubmission.setScore(0);
       // quizSubmission.setStartedAt(LocalDateTime.now());
        quizSubmission=quizSubmissionRepository.save(quizSubmission);
/*
        Duration elapsed = Duration.between(quizSubmission.getStartedAt(), LocalDateTime.now());
        if (elapsed.toMinutes() > quiz.getDuration()) {
            throw new RuntimeException("Time is up. You cannot submit the quiz.");
        }
*/
        int totalScore=0;
        for(AnswerDTO dto:dtos)
        {
            Long questionId=dto.getQuestionId();
            String studentAnswer=dto.getAnswer();

            QuizQuestion question=quizQuestionRepository.findById(questionId)
                    .orElseThrow(()->new RuntimeException("Question not found"));

            StudentAnswer answer=new StudentAnswer();
            answer.setSubmission(quizSubmission);
            answer.setQuestion(question);

            switch (question.getQuestionType())
            {
                case MCQ :
                case TRUE_FALSE:
                    answer.setOptionAnswer(studentAnswer);
                    boolean isCorrect =studentAnswer.equals(question.getAnswer());
                    answer.setCorrect(isCorrect);
                    int points = isCorrect ? question.getPoints() : 0;
                    answer.setScore(points);
                    totalScore+=points;
                    break;
                case SHORT_ANSWER:
                    answer.setShortAnswer(studentAnswer);
                    answer.setCorrect(false);
                    answer.setScore(0);
                    break;
            }


        studentAnswerRepository.save(answer);


        }




        quizSubmission.setScore(totalScore);
        quizSubmissionRepository.save(quizSubmission);




    }

    public QuizResponseDTO getQuizForStudent(Long quizId, Long courseId, String studentEmail) {
        Student student=studentRepository.findStudentByEmail(studentEmail)
                .orElseThrow(()->new RuntimeException("Student not found"));

        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isEnrolled=studentCourseRepository.isStudentEnrolledInCourse(student,course);
        if(!isEnrolled)
        {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        Quiz quiz=quizRepository.getQizIsAvailableById(courseId,quizId, LocalDateTime.now())
                .orElseThrow(()->new RuntimeException("Quiz not found"));





        QuizResponseDTO dto = quizMapper.toQuizResponseDTO(quiz);
        dto.setShowResults(quiz.getShowResults());

        for(QuestionResponseDTO question: dto.getQuizQuestions())
        {
            question.setCorrectAnswer(null);
        }
        return dto;
    }

    public List<QuizDTO> getAllQuizForStudent( Long courseId, String studentEmail) {
        Student student=studentRepository.findStudentByEmail(studentEmail)
                .orElseThrow(()->new RuntimeException("Student not found"));

        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isEnrolled=studentCourseRepository.isStudentEnrolledInCourse(student,course);
        if(!isEnrolled)
        {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        List<Quiz> quizzes=quizRepository.findAllQuizzesByCourse(courseId);

        List<QuizDTO> quizDTOList=new ArrayList<>();
        for(Quiz dto:quizzes)
        {

            boolean isSubmitted=false;
            QuizDTO quizDTO= quizMapper.toQuizDTO(dto);
            for (QuizSubmission quizSubmission:dto.getSubmissions())
            {

                if(quizSubmission.getStudent().getStudentId().equals(student.getStudentId()))
                {
                    quizDTO.setStatus("submitted");
                    isSubmitted=true;
                    break;
                }
            }
            if(dto.getStartDate()!=null && dto.getEndDate()!=null) {
                if (dto.getStartDate().isAfter(LocalDateTime.now()) || dto.getEndDate().isBefore(LocalDateTime.now())) {
                    quizDTO.setDeleted(true);
                    ; // skip quizzes that are not available
                }
            }else if(dto.getStartDate()==null && dto.getEndDate()==null) {
                quizDTO.setDeleted(true);
            }

            if(!isSubmitted)
            {
                quizDTO.setStatus("unsubmitted");
            }

            quizDTOList.add(quizDTO);
        }
        return quizDTOList;
    }

    public QuizResult getQuizResult(Long quizId, Long courseId, String studentEmail) {
        Student student=studentRepository.findStudentByEmail(studentEmail)
                .orElseThrow(()->new RuntimeException("Student not found"));

        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isEnrolled=studentCourseRepository.isStudentEnrolledInCourse(student,course);
        if(!isEnrolled)
        {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        Quiz quiz=quizRepository.findQuizByCourseAndQuizId(courseId,quizId)
                .orElseThrow(()->new RuntimeException("Quiz not found"));

//        if(quiz.getShowResults()==null || !quiz.getShowResults())
//        {
//           // quiz.getQuestions().stream().forEach(e->e.setAnswer(null));
//            System.out.println("Quiz results are not available yet");
//        }
//
//        System.out.println("results are available : " + quiz.getShowResults());
//        System.out.println("Quiz ID: " +quiz.getId() );
//        System.out.println("Student ID: " +student.getStudentId() );
//        System.out.println("course ID: " +course.getCourseId() );
        QuizSubmission quizSubmission=quizSubmissionRepository.findByQuizIdAndStudentId(quiz.getId(),student.getStudentId())
                .orElseThrow(()->new RuntimeException("Quiz submission not found"));

        List<StudentAnswer> answers=studentAnswerRepository.findAllBySubmission(quizSubmission);

        QuizResult result=new QuizResult();
        result.setQuiz(quizMapper.toQuizResponseDTO(quiz,quiz.getShowResults()));
        result.setMaxMarks(quiz.getTotalDegree());
        result.setTotalMarks(quizSubmission.getScore());

        /*
            questionId , studentanswer


         */
        Map<Long,String> studentAnswers=new HashMap<>();
        for(StudentAnswer answer:answers)
        {

            studentAnswers.put(answer.getQuestion().getId(),answer.getOptionAnswer());
        }
        result.setStudentAnswers(studentAnswers);

        return result;
    }

    public QuizSubmissionInstructor getQuizSubmission(Long courseId, String instructorEmail, Long quizId, Long studentId) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isInstructor=courseRepository.isInstructorOfCourse(instructorEmail,courseId);
        if(!isInstructor)
        {
            throw new RuntimeException("Instructor is not enrolled in this course");
        }
        Quiz quiz=quizRepository.findQuizByCourseAndQuizId(courseId,quizId)
                .orElseThrow(()->new RuntimeException("Quiz not found"));


        QuizSubmission quizSubmission=quizSubmissionRepository.findByQuizIdAndStudentId(quizId,studentId)
                .orElseThrow(()->new RuntimeException("Quiz submission not found"));

        List<StudentAnswer> studentAnswers=studentAnswerRepository.findAllBySubmission(quizSubmission);

        QuizSubmissionInstructor quizSubmissionInstructor=new QuizSubmissionInstructor();
        quizSubmissionInstructor.setQuizId(quizId);
        quizSubmissionInstructor.setQuizTitle(quiz.getTitle());
        quizSubmissionInstructor.setStudentId(studentId);
        quizSubmissionInstructor.setSubmissionTime(quizSubmission.getSubmittedAt());
        quizSubmissionInstructor.setScoredMarks(quizSubmission.getScore());
        quizSubmissionInstructor.setTotalQuestions(quiz.getQuestions().size());

        Map<Long,String> studentAnswerMap=new HashMap<>();
        List<QuizSubmissionInstructor.shortAnswer> shortAnswerList=new ArrayList<>();
        for (StudentAnswer answer:studentAnswers)
        {
            if(answer.getQuestion().getQuestionType()== QuestionType.SHORT_ANSWER) {
                QuizSubmissionInstructor.shortAnswer shortAnswer=new QuizSubmissionInstructor.shortAnswer();
                shortAnswer.setQuestionId(answer.getQuestion().getId());
                shortAnswer.setAnswer(answer.getShortAnswer());
                shortAnswer.setText(answer.getQuestion().getQuestionText());
                shortAnswerList.add(shortAnswer);
            }
            else {
                studentAnswerMap.put(answer.getQuestion().getId(),answer.getOptionAnswer());
            }
        }
        quizSubmissionInstructor.setStudentAnswers(studentAnswerMap);
        quizSubmissionInstructor.setStudentAnswersShort(shortAnswerList);
        return quizSubmissionInstructor;
    }

    public List<?> getAllStudentSubmission(Long quizId, Long courseId, String instructorEmail) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isInstructor=courseRepository.isInstructorOfCourse(instructorEmail,courseId);
        if(!isInstructor)
        {
            throw new RuntimeException("Instructor is not enrolled in this course");
        }

        List<QuizSubmission> quizSubmissions=quizSubmissionRepository.findAllByCourse(course,quizId);
        List<Map<String,Object>> quizSubmissionInstructors=new ArrayList<>();
        for (QuizSubmission quizSubmission:quizSubmissions)
        {
            Map<String,Object> quizSubmissionInstructor=new HashMap<>();
            quizSubmissionInstructor.put("studentId",quizSubmission.getStudent().getStudentId());
            quizSubmissionInstructor.put("submissionTime",quizSubmission.getSubmittedAt());
            quizSubmissionInstructor.put("username",quizSubmission.getStudent().getUser().getFirstName()+" "+quizSubmission.getStudent().getUser().getLastName());
            quizSubmissionInstructor.put("email",quizSubmission.getStudent().getUser().getEmail());
            quizSubmissionInstructors.add(quizSubmissionInstructor);

        }

        return quizSubmissionInstructors;
    }

    public ByteArrayInputStream  downloadQuizResult(Long quizId, Long courseId, String instructorEmail) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));
        boolean isInstructor=courseRepository.isInstructorOfCourse(instructorEmail,courseId);
        if(!isInstructor)
        {
            throw new RuntimeException("Instructor is not enrolled in this course");
        }

        List<QuizSubmission> quizSubmissions=quizSubmissionRepository.findAllByCourse(course,quizId);
        List<Map<String,Object>> quizSubmissionInstructors=new ArrayList<>();
        for (QuizSubmission quizSubmission:quizSubmissions)
        {
            Map<String,Object> quizSubmissionInstructor=new HashMap<>();
            quizSubmissionInstructor.put("studentId",quizSubmission.getStudent().getStudentId());
            quizSubmissionInstructor.put("submissionTime",quizSubmission.getSubmittedAt());
            quizSubmissionInstructor.put("username",quizSubmission.getStudent().getUser().getFirstName()+" "+quizSubmission.getStudent().getUser().getLastName());
            quizSubmissionInstructor.put("email",quizSubmission.getStudent().getUser().getEmail());
            quizSubmissionInstructor.put("score",quizSubmission.getScore());
            quizSubmissionInstructor.put("totalDegree",quizSubmission.getQuiz().getTotalDegree());
            quizSubmissionInstructors.add(quizSubmissionInstructor);

        }
        ByteArrayInputStream res=fileService.saveQuizResults(quizSubmissionInstructors);
        return res;
    }
}
