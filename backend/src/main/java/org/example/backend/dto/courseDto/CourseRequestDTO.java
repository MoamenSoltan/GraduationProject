package org.example.backend.dto.courseDto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CourseType;
import org.example.backend.enums.CourseYear;
import org.example.backend.enums.SemesterName;

@Setter
@Getter
public class CourseRequestDTO {
    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Course name must not exceed 100 characters")
    private String courseName;

    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 10, message = "Course code must be between 3 and 10 characters")
    private String courseCode;

    @Min(value = 1, message = "Credit must be at least 1")
    @Max(value = 10, message = "Credit cannot exceed 10")
    private int credit;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Min(value = 1, message = "Maximum students must be at least 1")
    @Max(value = 500, message = "Maximum students cannot exceed 500")
    private int maxStudents = 200;

    @NotNull(message = "Year is required")
    private CourseYear year;

    @NotNull(message = "Course type is required")
    private CourseType type;

    @Size(max = 255, message = "Schedule must not exceed 255 characters")
    private String schedule;

    // Foreign key references
    @NotNull(message = "Department ID is required")
    private Integer departmentId;

    private Integer instructorId;  // Optional

    private Long prerequisiteCourseId;  // Optional

    @NotNull(message = "Semester year level is required")
    private Integer yearLevel;  // Part of composite key for Semester

    @NotNull(message = "Semester name is required")
    private SemesterName semesterName;  // Part of composite key for Semester
}