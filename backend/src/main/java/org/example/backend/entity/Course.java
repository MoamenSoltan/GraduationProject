package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CourseType;
import org.example.backend.enums.CourseYear;

import java.time.LocalDateTime;


@Entity
@Table(name = "courses")
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;
    private String courseName;
    @Column(unique = true)
    private String courseCode;
    private int credit;
    @Column(name = "course_description")
    private String description;
    private LocalDateTime createdAt = LocalDateTime.of(2023, 5, 9, 0, 0, 0); // Year, Month, Day, Hour, Minute, Second
    @Column(name = "max_students")
    private int maxStudents = 200;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private CourseYear year;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CourseType type;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "student_enrolled")
    private int studentEnrolled = 0;

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
    @JoinColumns({
            @JoinColumn(name = "semester_year_level", referencedColumnName = "year_level"),
            @JoinColumn(name = "semester_name", referencedColumnName = "semester_name")
    })
    private Semester semester;



}
