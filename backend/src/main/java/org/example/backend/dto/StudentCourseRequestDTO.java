package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

@Setter
@Getter
public class StudentCourseRequestDTO {

    private Long courseId;
//    private Integer yearLevel;         // Part of composite key for Semester
//    private SemesterName semesterName; // Part of composite key for Semester
    private Double grade = 0.0;
}
