package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_submissions")
@Setter
@Getter
public class TaskSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private int id;
    @Column(name = "submission_date")
    private LocalDateTime submittedAt = LocalDateTime.now();
    @Column(name = "submission_file")
    private String attachment;
    @Column(name = "grade")
    private double grade = 0.0;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

}
