package org.example.backend.dto.semesterDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

import java.time.LocalDateTime;
@Getter
@Setter
public class SemesterResponseDTO {
    private Integer yearLevel;           // Part of composite key
    private SemesterName semesterName;   // Part of composite key
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
