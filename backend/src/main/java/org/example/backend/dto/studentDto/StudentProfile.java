package org.example.backend.dto.studentDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.enums.DepartmentName;
import org.example.backend.enums.LevelYear;

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
    private LevelYear grade;
    private String address;
    private String city;
    private String country;
    private String firstName;
    private String lastName;


}
