package org.example.backend.dto.taskDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseTaskSubmissionDTO {
    private int id;
    private String attachment;
    private String submittedAt;
    private double grade;
    private int taskId;
    private String studentEmail;
}
