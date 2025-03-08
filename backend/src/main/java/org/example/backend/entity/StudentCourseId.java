package org.example.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class StudentCourseId {
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "semester_year_level")
    private Integer yearLevel;       // Part of Semester composite key

    @Column(name = "semester_name")
    private String semesterName;
}
