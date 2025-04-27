package org.example.backend.dto.studentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseGradeDTO {
    private Long studentId;
    private Long courseId;
    private Number totalTaskGrade;
    private Number totalQuizGrade;
    private Number finalDegree;
    private String username ;
    private String email;
    private String courseName;



}
