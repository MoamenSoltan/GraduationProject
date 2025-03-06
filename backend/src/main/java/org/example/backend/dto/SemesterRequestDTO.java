package org.example.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

import java.time.LocalDateTime;

@Setter
@Getter
public class SemesterRequestDTO {
    @NotNull(message = "Year level is required")
    @Min(value = 1, message = "Year level must be at least 1")
    private Integer yearLevel;

    @NotNull(message = "Semester name is required")
    private SemesterName semesterName;


    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    private Boolean isActive;
}
