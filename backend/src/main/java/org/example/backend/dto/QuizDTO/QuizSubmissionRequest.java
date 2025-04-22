package org.example.backend.dto.QuizDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class QuizSubmissionRequest {
    private Map<Long,String> answers;
    private int score;
}
