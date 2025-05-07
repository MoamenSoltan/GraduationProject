package org.example.backend.dto.taskDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
public class ResponseTaskDTO {
    private int id;
    private String taskName;
    private double maxGrade;
    private String description;
    private String attachment;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDate deadline;
    private Long courseId;
    private String status;
}
