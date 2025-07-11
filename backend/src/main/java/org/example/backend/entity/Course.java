package org.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CourseType;
import org.example.backend.enums.LevelYear;

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
    private Long courseId;
    private String courseName;
    @Column(unique = true)
    private String courseCode;
    private int credit;
    @Column(name = "course_description")
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();// Year, Month, Day, Hour, Minute, Second
    @Column(name = "max_students")
    private int maxStudents = 200;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private LevelYear year;

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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StudentCourse> studentCourses;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Quiz> quizList;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Material> materials;



}
