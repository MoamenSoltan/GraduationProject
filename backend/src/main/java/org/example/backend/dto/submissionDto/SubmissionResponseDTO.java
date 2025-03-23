package org.example.backend.dto.submissionDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.AdmissionStatus;


@Setter
@Getter
public class SubmissionResponseDTO {
    private String firstname;
    private String lastname;
    private Integer id;
    private String highSchoolName;
    private String graduationYear;
    private Double highSchoolGpa;
    private AdmissionStatus status;
    private String highSchoolCertificate;
    private String phoneNumber;
    private String idPhoto;
    private String personalPhoto;
    private String country;
    private String city;
    private String address;
}
