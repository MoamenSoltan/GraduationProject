package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.FeesStatus;
import org.example.backend.enums.LevelYear;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
@Setter
@Getter
public class Student  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    @Enumerated(EnumType.STRING)
    private LevelYear academicYear;
    private double gpa;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private FeesStatus feesStatus;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy ="student",cascade = CascadeType.ALL)
    private List<StudentCourse> studentCourse;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "submission_request_id")
    private SubmissionRequest submissionRequest;
}

