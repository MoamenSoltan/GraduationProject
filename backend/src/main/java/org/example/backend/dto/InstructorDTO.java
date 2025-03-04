package org.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.Department;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.User;
import org.example.backend.enums.DepartmentName;
import org.example.backend.enums.GenderType;

@Setter
@Getter

@Data
public class InstructorDTO {
    private int instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String password;
    private DepartmentName departmentName;
//    @JsonProperty("isHeadOfDepartment")
    private boolean isHeadOfDepartment=true;
    private DepartmentDTO department;
    private DepartmentDTO managedDepartment;

    public boolean isHeadOfDepartment() {
        return isHeadOfDepartment;
    }

    public void setHeadOfDepartment(boolean headOfDepartment) {
        isHeadOfDepartment = headOfDepartment;
    }
}
