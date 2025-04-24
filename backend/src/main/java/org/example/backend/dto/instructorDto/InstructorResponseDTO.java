package org.example.backend.dto.instructorDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.enums.GenderType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class InstructorResponseDTO {
    private Integer instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private GenderType gender;
    private String personalImage;
    private String bio;
    private DepartmentDTO department;
    private DepartmentDTO managedDepartment;
    private List<CourseDTO> courses ;
}
