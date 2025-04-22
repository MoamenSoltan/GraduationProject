package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.instructorDto.InstructorDTO;

import java.time.LocalDateTime;

@Setter
@Getter
public class QuizDTO {
    @JsonProperty("id")
    private Long quizId;
    @JsonProperty("name")
    private String quizName;
    @JsonProperty("description")
    private String quizDescription;
    @JsonProperty("totalMarks")
    private int totalMarks;
    @JsonProperty("time")
    private int timeLimit;
    private LocalDateTime createdAt;
    private InstructorDTO instructor;
    private boolean isDeleted;


}
