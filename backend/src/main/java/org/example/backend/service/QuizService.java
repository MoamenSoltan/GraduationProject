package org.example.backend.service;

import org.example.backend.dto.QuizDTO.*;
import org.example.backend.entity.*;
import org.example.backend.mapper.QuizMapper;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.QuizQuestionRepository;
import org.example.backend.repository.QuizRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizMapper quizMapper=new QuizMapper();

    public QuizService(QuizRepository quizRepository, CourseRepository courseRepository, InstructorRepository instructorRepository, QuizQuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.questionRepository = questionRepository;
    }

    public String createQuiz(Long courseId, String instructorEmail , QuizRequestDTO quizRequestDTO) {
        Instructor instructor = instructorRepository.getByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findCourseByInstructorAndCourseId(instructor,courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Quiz quiz=quizMapper.toEntityQuiz(quizRequestDTO);





        quiz.setCourse(course);


        List<QuizQuestion> questions=new ArrayList<>();
        for (QuizQuestionDTO questionDTO:quizRequestDTO.getQuizQuestions())
        {
            QuizQuestion question = quizMapper.toEntityQuizQuestion(questionDTO);
            question.setQuiz(quiz);
            questions.add(question);
        }
        quiz.setQuestions(questions);


        quizRepository.save(quiz);

        return "done : "+course.getCourseCode();
    }

    public QuizResponseDTO getQuiz(Long courseId, String instructorEmail, Long quizId) {
        Instructor instructor = instructorRepository.getByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findCourseByInstructorAndCourseId(instructor,courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Quiz quiz=quizRepository.findQuizByCourseAndQuizId(course,quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        return quizMapper.toQuizResponseDTO(quiz);
    }

    public String deleteQuiz(Long courseId, String instructorEmail, Long quizId) {
        Instructor instructor = instructorRepository.getByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findCourseByInstructorAndCourseId(instructor,courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Quiz quiz=quizRepository.findQuizByCourseAndQuizId(course,quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quizRepository.delete(quiz);

        return "Quiz deleted successfully";
    }


    public List<QuizResponseDTO> getAllQuizzes(Long courseId, String instructorEmail) {
        Instructor instructor = instructorRepository.getByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findCourseByInstructorAndCourseId(instructor,courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Quiz> quizzes=quizRepository.findAllByCourse(course);

        List<QuizResponseDTO> quizResponseDTOs=new ArrayList<>();
        for (Quiz quiz:quizzes)
        {
            QuizResponseDTO quizResponseDTO=quizMapper.toQuizResponseDTO(quiz);
            quizResponseDTOs.add(quizResponseDTO);
        }
        return quizResponseDTOs;
    }
    @Transactional
    public String updateQuestionsQuiz(Long courseId, String instructorEmail, Long quizId, Long questionId, QuestionResponseDTO questionDTO) {
        Instructor instructor = instructorRepository.getByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findCourseByInstructorAndCourseId(instructor, courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Quiz quiz = quizRepository.findQuizByCourseAndQuizId(course, quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Find question by id
        QuizQuestion question= questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        if(quiz.getQuestions()==null)
        {
            throw new RuntimeException("Quiz has no questions");
        }

//           if(questionId>=0 && questionId<quiz.getQuestions().size())
//           {
//                question = quiz.getQuestions().get(Math.toIntExact(questionId));
//
//           }else {
//                throw new RuntimeException("Question not found");
//           }

//        System.out.println("questionId: " + questionId);
//        System.out.println("question: " + question.getQuestionText());
//        System.out.println("options: " + (question.getOptions() != null ? question.getOptions().size() : 0));

        question.setId(question.getId());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(questionDTO.getCorrectAnswer());
        question.setPoints(questionDTO.getPoints());
        question.setQuestionType(questionDTO.getQuestionType());

        // Clear old options without replacing the list reference
        if (question.getOptions() == null) {
            question.setOptions(new ArrayList<>());
        } else {
            question.getOptions().clear();
        }

        // Add new options
        for (OptionResponseDTO optionDTO : questionDTO.getOptions()) {
            QuestionOptionEntity optionEntity = quizMapper.toUpdateEntityQuestionOption(optionDTO);
            optionEntity.setQuestion(question);

            question.getOptions().add(optionEntity);
        }




        // Save the parent quiz to cascade changes
//        quizRepository.save(quiz);

        questionRepository.save(question);

        return "done";
    }

    @Transactional
    public String deleteQuestion(Long courseId, String instructorEmail, Long quizId, Long questionId) {
        Instructor instructor = instructorRepository.getByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findCourseByInstructorAndCourseId(instructor, courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Quiz quiz = quizRepository.findQuizByCourseAndQuizId(course, quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            throw new RuntimeException("Quiz has no questions");
        }

        // Log all question IDs for debugging
//        System.out.println("Available question IDs: " + quiz.getQuestions().stream()
//                .map(QuizQuestion::getId)
//                .toList());

        QuizQuestion question = quiz.getQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Question not found"));

//         Remove the question from the quiz
        quiz.getQuestions().remove(question);


        quizRepository.save(quiz);

        // Delete the question
        questionRepository.delete(question);

        return "Question deleted successfully";
    }
}
