package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.FeesStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")

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

    public FeesStatus getFeesStatus() {
        return feesStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setFeesStatus(FeesStatus feesStatus) {
        this.feesStatus = feesStatus;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public SubmissionRequest getSubmissionRequest() {
        return submissionRequest;
    }

    public void setSubmissionRequest(SubmissionRequest submissionRequest) {
        this.submissionRequest = submissionRequest;
    }

    @OneToOne
    @JoinColumn(name = "submission_request_id")
    private SubmissionRequest submissionRequest;
}

