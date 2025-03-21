package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.GenderType;
import org.example.backend.enums.LevelYear;
import org.example.backend.enums.RoleType;

import java.time.LocalDateTime;

@Entity
@Table(name = "submission_request")
@Setter
@Getter
public class SubmissionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType userType;
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private LevelYear academicYear ;
    private String highSchoolName;
    private String graduationYear;
    private Double highSchoolGpa;
    private String highSchoolCertificate;
    private String phoneNumber;
    private String idPhoto;
    private String personalPhoto;
    private String country;
    private String city;
    private String address;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdmissionStatus admissionStatus;
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();



}
