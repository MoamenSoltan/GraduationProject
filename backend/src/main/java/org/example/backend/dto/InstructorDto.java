package org.example.backend.dto;

import org.example.backend.entity.Department;
import org.example.backend.entity.User;
import org.example.backend.enums.DepartmentName;
import org.example.backend.enums.GenderType;

public class InstructorDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private GenderType gender;
    private DepartmentName departmentName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public DepartmentName getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(DepartmentName departmentName) {
        this.departmentName = departmentName;
    }
}
