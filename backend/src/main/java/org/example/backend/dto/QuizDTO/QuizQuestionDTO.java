package org.example.backend.dto.QuizDTO;

import org.example.backend.enums.QuestionType;

import java.util.List;

public class QuizQuestionDTO {

    private String questionText;
    private QuestionType questionType=QuestionType.MCQ;
    private List<String> options;
    private String correctAnswer;
}
