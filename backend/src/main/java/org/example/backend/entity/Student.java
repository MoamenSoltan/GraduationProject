package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.FeesStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Setter
@Getter
public class Student  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private int academicYear;
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



    @OneToOne
    @JoinColumn(name = "submission_request_id")
    private SubmissionRequest submissionRequest;
}

