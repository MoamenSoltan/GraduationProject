package org.example.backend.dto.studentDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.submissionDto.SubmissionResponseDTO;
import org.example.backend.enums.FeesStatus;
import org.example.backend.enums.GenderType;
import org.example.backend.enums.LevelYear;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class StudentResponseDTO {
    private Long studentId;
    private LevelYear academicYear;
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
    private List<String> roles;
    private List<CourseDTO> courses;

}
