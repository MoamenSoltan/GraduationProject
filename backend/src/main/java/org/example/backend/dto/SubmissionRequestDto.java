package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


public class SubmissionRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userType;
    private Integer academicYear;
    private String highSchoolName;
    private String graduationYear;
    private Double highSchoolGpa;
    private MultipartFile highSchoolCertificate;
    private MultipartFile idPhoto;
    private MultipartFile personalPhoto;
    private String phoneNumber;
    private String country;
    private String city;
    private String address;


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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
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

    public MultipartFile getHighSchoolCertificate() {
        return highSchoolCertificate;
    }

    public void setHighSchoolCertificate(MultipartFile highSchoolCertificate) {
        this.highSchoolCertificate = highSchoolCertificate;
    }

    public MultipartFile getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(MultipartFile idPhoto) {
        this.idPhoto = idPhoto;
    }

    public MultipartFile getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(MultipartFile personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
