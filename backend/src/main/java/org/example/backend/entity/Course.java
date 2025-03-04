package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;
    private String courseName;
    private String courseCode;
    private int credit;
    @Column(name = "course_description")
    private String description;
    private LocalDateTime createdAt=LocalDateTime.now();
    //relations between entity
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "prerequisites_course_id")
    private Course prerequisiteCourse;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;



}
