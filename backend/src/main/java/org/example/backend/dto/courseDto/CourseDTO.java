package org.example.backend.dto.courseDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.semesterDto.SemesterDTO;
import org.example.backend.enums.LevelYear;

@Setter
@Getter
public class CourseDTO {
    private Long courseId;
    private String courseName;
    private String courseCode;
    private LevelYear grade;
    private SemesterDTO semester;
}
