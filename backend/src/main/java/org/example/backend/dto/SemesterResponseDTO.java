package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

import java.time.LocalDateTime;
@Getter
@Setter
public class SemesterResponseDTO {
    private Integer semesterId;
    private Integer yearLevel;
    private SemesterName semesterName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
