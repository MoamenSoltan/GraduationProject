package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.QuestionType;

import java.util.List;

@Setter
@Getter
public class QuestionResponseDTO {
    @JsonProperty("id")
    private Long questionId;
    @JsonProperty("question")
    private String questionText;
    private QuestionType questionType;
    private List<OptionResponseDTO> options;
    private String correctAnswer;
    @JsonProperty("score")
    private int points;
}
