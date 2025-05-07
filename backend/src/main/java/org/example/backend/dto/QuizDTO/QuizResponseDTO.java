package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class QuizResponseDTO {
    @JsonProperty("id")
    private Long quizId;
    @JsonProperty("name")
    private String quizName;
    @JsonProperty("description")
    private String quizDescription;
    @JsonProperty("time")
    private int quizTime;
    @JsonProperty("questions")
    private List<QuestionResponseDTO> quizQuestions;
    @JsonProperty("totalDegree")
    private int totalDegree;
    private String status;
}
