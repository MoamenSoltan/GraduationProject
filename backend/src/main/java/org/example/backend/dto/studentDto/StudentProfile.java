package org.example.backend.dto.studentDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.enums.DepartmentName;

import java.util.List;

@Setter
@Getter
public class StudentProfile {
    private Long id;
    private String username;
    private String profileImage;
    private String email;
    private List<CourseDTO> courses;
    private Integer totalCredit;
    private DepartmentName department;
    private Double gpa;
    private int grade;
    private String address;


}
