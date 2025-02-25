package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.util.AdmissionStatus;
import org.example.backend.util.RoleType;
import org.hibernate.usertype.UserType;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "submission_request")
//@Getter
//@Setter
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
    private Integer academicYear;
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


    //

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getUserType() {
        return userType;
    }

    public void setUserType(RoleType userType) {
        this.userType = userType;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public String getHighSchoolName() {
        return highSchoolName;
    }

    public void setHighSchoolName(String highSchoolName) {
        this.highSchoolName = highSchoolName;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Double getHighSchoolGpa() {
        return highSchoolGpa;
    }

    public void setHighSchoolGpa(Double highSchoolGpa) {
        this.highSchoolGpa = highSchoolGpa;
    }

    public String getHighSchoolCertificate() {
        return highSchoolCertificate;
    }

    public void setHighSchoolCertificate(String highSchoolCertificate) {
        this.highSchoolCertificate = highSchoolCertificate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AdmissionStatus getAdmissionStatus() {
        return admissionStatus;
    }

    public void setAdmissionStatus(AdmissionStatus admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
