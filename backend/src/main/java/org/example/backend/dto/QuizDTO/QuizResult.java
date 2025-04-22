package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class QuizResult {
    private QuizResponseDTO quiz;
    private int totalMarks;
    private int maxMarks;
    @JsonProperty("answers")
    private Map<Long,String> studentAnswers;
}
