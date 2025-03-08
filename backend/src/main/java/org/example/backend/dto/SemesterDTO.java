package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CourseYear;

@Setter
@Getter
public class SemesterDTO {

        private Integer yearLevel;
    private String semesterName;
    private int year;
}
