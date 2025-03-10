package org.example.backend.mapper;

import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.User;


public class InstructorMapper {

    public static Instructor requestToEntity(InstructorRequestDTO requestDTO)
    {
        Instructor instructor= new Instructor();

        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setGender(requestDTO.getGender());

        instructor.setUser(user);

        return instructor;
    }

    public static InstructorResponseDTO  entityToResponseDTO(Instructor entity)
    {
        InstructorResponseDTO responseDTO = new InstructorResponseDTO();
        responseDTO.setInstructorId(entity.getInstructorId());
        if (entity.getUser() != null) {
            responseDTO.setFirstName(entity.getUser().getFirstName());
            responseDTO.setLastName(entity.getUser().getLastName());
            responseDTO.setEmail(entity.getUser().getEmail());
            responseDTO.setGender(entity.getUser().getGender());
        }

        if (entity.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDepartmentId(entity.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(entity.getDepartment().getDepartmentName());
            responseDTO.setDepartment(departmentDTO);
        }

        if(entity.getManagedDepartment() != null)
        {
            DepartmentDTO managedDepartmentDTO = new DepartmentDTO();
            managedDepartmentDTO.setDepartmentId(entity.getManagedDepartment().getDepartmentId());
            managedDepartmentDTO.setDepartmentName(entity.getManagedDepartment().getDepartmentName());
            responseDTO.setManagedDepartment(managedDepartmentDTO);
        }

        return responseDTO;
    }
}