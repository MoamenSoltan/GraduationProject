package org.example.backend.dto.instructorDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.dto.DepartmentDTO;
import org.example.backend.enums.GenderType;

@Setter
@Getter
public class InstructorResponseDTO {
    private Integer instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private GenderType gender;
    private DepartmentDTO department;
    private DepartmentDTO managedDepartment;
}
