package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_course")
@Setter
@Getter
public class StudentCourse {

    @EmbeddedId
    private StudentCourseId id;
    private Double degree;
    private LocalDateTime enrollmentDate;

    public StudentCourse()
    {
        this.enrollmentDate=LocalDateTime.now();
    }
    // relationship between entities

    @ManyToOne
    @MapsId("studentId") // Maps studentId part of the composite key
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId") // Maps courseId part of the composite key
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("semesterId") // Maps to the composite key
    @JoinColumns({
            @JoinColumn(name = "semester_year_level", referencedColumnName = "year_level"),
            @JoinColumn(name = "semester_name", referencedColumnName = "semester_name")
    })
    private Semester semester;
}
