package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CourseType;
import org.example.backend.enums.CourseYear;

import java.time.LocalDateTime;
@Setter
@Getter
public class CourseResponseDTO {

    private Long courseId;
    private String courseName;
    private String courseCode;
    private int credit;
    private String description;
    private int maxStudents;
    private CourseYear year;
    private CourseType type;
    private String schedule;
    private int studentEnrolled;
    private LocalDateTime createdAt;

    // Nested DTOs for relationships
    private DepartmentDTO department;
    private InstructorDTO instructor; // Nullable
    private CourseDTO prerequisiteCourse; // Nullable
    private SemesterDTO semester;
}
