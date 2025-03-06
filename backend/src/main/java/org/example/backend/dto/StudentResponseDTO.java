package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.FeesStatus;
import org.example.backend.enums.GenderType;

import java.time.LocalDateTime;

@Setter
@Getter
public class StudentResponseDTO {
    private Integer studentId;
    private Integer academicYear;
    private Double gpa;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private GenderType gender;
    private DepartmentDTO department;
    private FeesStatus feesStatus;
    private LocalDateTime createdAt;
    private SubmissionResponseDTO submissionResponseDTO;
}
