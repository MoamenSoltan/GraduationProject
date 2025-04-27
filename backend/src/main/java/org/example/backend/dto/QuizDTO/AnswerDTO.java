package org.example.backend.dto.QuizDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerDTO {
    private Long questionId;
    private String answer;
}
