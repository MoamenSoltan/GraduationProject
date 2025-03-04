package org.example.backend.mapper;

import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.InstructorDTO;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.User;
import org.example.backend.enums.GenderType;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public Instructor mapToInstructor(InstructorDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setGender(GenderType.valueOf(dto.getGender().toUpperCase()));
        user.setPassword(dto.getPassword());

        Instructor instructor = new Instructor();
        instructor.setUser(user);
        return instructor;
    }

    // Optional: Add method to convert entity to DTO for response
    public InstructorDTO mapToDto(Instructor instructor) {
        InstructorDTO dto = new InstructorDTO();
        dto.setInstructorId(instructor.getInstructorId());
        dto.setFirstName(instructor.getUser().getFirstName());
        dto.setLastName(instructor.getUser().getLastName());
        dto.setEmail(instructor.getUser().getEmail());
        dto.setGender(instructor.getUser().getGender().name());
        dto.setDepartmentName(instructor.getDepartment().getDepartmentName());
        dto.setHeadOfDepartment(instructor.getManagedDepartment() != null);

        DepartmentDTO deptDto = new DepartmentDTO();
        deptDto.setDepartmentId(instructor.getDepartment().getDepartmentId());
        deptDto.setDepartmentName(instructor.getDepartment().getDepartmentName());
        deptDto.setCreatedAt(instructor.getDepartment().getCreatedAt());
        dto.setDepartment(deptDto);

        if (instructor.getManagedDepartment() != null) {
            DepartmentDTO managedDeptDto = new DepartmentDTO();
            managedDeptDto.setDepartmentId(instructor.getManagedDepartment().getDepartmentId());
            managedDeptDto.setDepartmentName(instructor.getManagedDepartment().getDepartmentName());
            managedDeptDto.setCreatedAt(instructor.getManagedDepartment().getCreatedAt());
            dto.setManagedDepartment(managedDeptDto);
        }

        return dto;
    }
}