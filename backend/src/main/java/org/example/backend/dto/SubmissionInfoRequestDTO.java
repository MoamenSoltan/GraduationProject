package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.GenderType;

@Setter
@Getter
public class SubmissionInfoRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String highSchoolName;
    private String graduationYear;
    private Double highSchoolGpa;
    private String phoneNumber;
    private String country;
    private String city;
    private String address;
    private GenderType gender;
}
