package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CourseYear;

@Setter
@Getter
public class CourseDTO {
    private Long courseId;
    private String courseName;
    private String courseCode;
    private CourseYear grade;
    private SemesterDTO semester;
}
