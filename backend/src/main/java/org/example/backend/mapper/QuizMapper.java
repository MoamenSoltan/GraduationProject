package org.example.backend.mapper;

import org.example.backend.dto.QuizDTO.*;
import org.example.backend.dto.instructorDto.InstructorDTO;
import org.example.backend.entity.QuestionOptionEntity;
import org.example.backend.entity.Quiz;
import org.example.backend.entity.QuizQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {

    public Quiz toEntityQuiz(QuizRequestDTO quizRequestDTO)
    {
        Quiz quiz=new Quiz();
        quiz.setTitle(quizRequestDTO.getQuizName());
        quiz.setDescription(quizRequestDTO.getQuizDescription());
        quiz.setDuration(quizRequestDTO.getQuizTime());
        quiz.setTotalDegree(quizRequestDTO.getTotalDegree());
        quiz.setShowResults(quizRequestDTO.getShowResults());

        return quiz;
    }

    public QuizQuestion toEntityQuizQuestion(QuizQuestionDTO quizQuestionDTO) {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestionType(quizQuestionDTO.getQuestionType());
        quizQuestion.setQuestionText(quizQuestionDTO.getQuestionText());
        quizQuestion.setAnswer(quizQuestionDTO.getCorrectAnswer());
        quizQuestion.setPoints(quizQuestionDTO.getPoints());

        List<QuestionOptionEntity> optionEntities = new ArrayList<>();
        if (quizQuestionDTO.getOptions() != null) {
            for (QuestionOptionDTO optionDTO : quizQuestionDTO.getOptions()) {
                QuestionOptionEntity optionEntity=this.toEntityQuestionOption(optionDTO);
                optionEntity.setQuestion(quizQuestion);

                optionEntities.add(optionEntity);
            }


        }
        quizQuestion.setOptions(optionEntities);
        return quizQuestion;
    }

    public QuestionOptionEntity toEntityQuestionOption(QuestionOptionDTO optionDTO) {
        QuestionOptionEntity optionEntity = new QuestionOptionEntity();
        optionEntity.setOption(optionDTO.getOptionText());
        return optionEntity;
    }

    public QuestionOptionEntity toUpdateEntityQuestionOption(OptionResponseDTO optionDTO) {
        QuestionOptionEntity optionEntity = new QuestionOptionEntity();
        optionEntity.setOption(optionDTO.getOptionText());
        optionEntity.setId(optionDTO.getOptionId());
        return optionEntity;
    }

    public QuizResponseDTO toQuizResponseDTO(Quiz quiz) {
        QuizResponseDTO quizResponseDTO = new QuizResponseDTO();
        quizResponseDTO.setQuizId(quiz.getId());
        quizResponseDTO.setQuizName(quiz.getTitle());
        quizResponseDTO.setQuizDescription(quiz.getDescription());
        quizResponseDTO.setQuizTime(quiz.getDuration());
        quizResponseDTO.setTotalDegree(quiz.getTotalDegree());

        List<QuizQuestion> questions = quiz.getQuestions();
        List<QuestionResponseDTO> questionDTOs = new ArrayList<>();
        for (QuizQuestion question : questions) {
            QuestionResponseDTO dto = new QuestionResponseDTO();
            dto.setQuestionId(question.getId());
            dto.setQuestionText(question.getQuestionText());
            dto.setQuestionType(question.getQuestionType());
            dto.setPoints(question.getPoints());
            dto.setCorrectAnswer(question.getAnswer());
            List<OptionResponseDTO> optionDTOs = new ArrayList<>();
            if(question.getOptions()!=null) {
                for (QuestionOptionEntity option : question.getOptions()) {
                    OptionResponseDTO optionDTO = new OptionResponseDTO();
                    optionDTO.setOptionId(option.getId());
                    optionDTO.setOptionText(option.getOption());
                    optionDTOs.add(optionDTO);
                }
                dto.setOptions(optionDTOs);
            }
            questionDTOs.add(dto);
        }
        quizResponseDTO.setQuizQuestions(questionDTOs);

        return quizResponseDTO;
    }
    public QuizResponseDTO toQuizResponseDTO(Quiz quiz,boolean showResults) {

        QuizResponseDTO quizResponseDTO = new QuizResponseDTO();
        quizResponseDTO.setQuizId(quiz.getId());
        quizResponseDTO.setQuizName(quiz.getTitle());
        quizResponseDTO.setQuizDescription(quiz.getDescription());
        quizResponseDTO.setQuizTime(quiz.getDuration());
        quizResponseDTO.setTotalDegree(quiz.getTotalDegree());

        List<QuizQuestion> questions = quiz.getQuestions();
        List<QuestionResponseDTO> questionDTOs = new ArrayList<>();
        if (showResults) {

            for (QuizQuestion question : questions) {
                QuestionResponseDTO dto = new QuestionResponseDTO();
                dto.setQuestionId(question.getId());
                dto.setQuestionText(question.getQuestionText());
                dto.setQuestionType(question.getQuestionType());
                dto.setPoints(question.getPoints());
                dto.setCorrectAnswer(question.getAnswer());
                List<OptionResponseDTO> optionDTOs = new ArrayList<>();
                if (question.getOptions() != null) {
                    for (QuestionOptionEntity option : question.getOptions()) {
                        OptionResponseDTO optionDTO = new OptionResponseDTO();
                        optionDTO.setOptionId(option.getId());
                        optionDTO.setOptionText(option.getOption());
                        optionDTOs.add(optionDTO);
                    }
                    dto.setOptions(optionDTOs);
                }
                questionDTOs.add(dto);
            }
            quizResponseDTO.setQuizQuestions(questionDTOs);
        }else {
            for (QuizQuestion question : questions) {
                QuestionResponseDTO dto = new QuestionResponseDTO();
                dto.setQuestionId(question.getId());
                dto.setQuestionText(question.getQuestionText());
                dto.setQuestionType(question.getQuestionType());
                dto.setPoints(question.getPoints());
                dto.setCorrectAnswer(null);
                List<OptionResponseDTO> optionDTOs = new ArrayList<>();
                if (question.getOptions() != null) {
                    for (QuestionOptionEntity option : question.getOptions()) {
                        OptionResponseDTO optionDTO = new OptionResponseDTO();
                        optionDTO.setOptionId(option.getId());
                        optionDTO.setOptionText(option.getOption());
                        optionDTOs.add(optionDTO);
                    }
                    dto.setOptions(optionDTOs);
                }
                questionDTOs.add(dto);
            }
            quizResponseDTO.setQuizQuestions(questionDTOs);
        }

        return quizResponseDTO;
    }

    public QuizDTO toQuizDTO(Quiz quiz) {
        QuizDTO dto=new QuizDTO();
        dto.setQuizId(quiz.getId());
        dto.setQuizName(quiz.getTitle());
        dto.setQuizDescription(quiz.getDescription());
        dto.setTimeLimit(quiz.getDuration());
        dto.setTotalMarks(quiz.getTotalDegree());
        dto.setCreatedAt(quiz.getCreatedAt());

        InstructorDTO instructorDTO=new InstructorDTO();
        instructorDTO.setInstructorId(quiz.getCourse().getInstructor().getInstructorId());
        instructorDTO.setEmail(quiz.getCourse().getInstructor().getUser().getEmail());
        instructorDTO.setFirstName(quiz.getCourse().getInstructor().getUser().getFirstName());
        instructorDTO.setLastName(quiz.getCourse().getInstructor().getUser().getLastName());
        dto.setInstructor(instructorDTO);

        return dto;
    }
}
