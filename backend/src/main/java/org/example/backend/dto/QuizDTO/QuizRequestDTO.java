package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class QuizRequestDTO {
    @JsonProperty("name")
    private String quizName;
    @JsonProperty("description")
    private String quizDescription;
    @JsonProperty("time")
    private int quizTime;
    @JsonProperty("questions")
    private List<QuizQuestionDTO> quizQuestions;
    @JsonProperty("totalDegree")
    private int totalDegree;
    @JsonProperty("showResults")
    private Boolean showResults = false;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;

}
