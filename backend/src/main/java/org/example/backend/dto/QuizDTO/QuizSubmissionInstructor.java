package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Setter
@Getter
public class QuizSubmissionInstructor {
    private Long studentId;
    private Long quizId;
    private String quizTitle;
    @JsonProperty("submittedAt")
    private LocalDateTime submissionTime;
    @JsonProperty("answers_MCQ")
    private Map<Long,String> studentAnswers;
    @JsonProperty("answers_short")
    private List<shortAnswer> studentAnswersShort;
    private int totalQuestions;
    @JsonProperty("mcqScore")
    private int scoredMarks;

    @Setter
    @Getter
    public static class shortAnswer {
        private Long questionId;
        private String answer;
        private String text;

    }
}
