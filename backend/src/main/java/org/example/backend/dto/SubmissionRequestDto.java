package org.example.backend.dto;

import lombok.*;
import org.example.backend.enums.GenderType;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
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
    private GenderType gender;

}
