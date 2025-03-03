package org.example.backend.service;

import org.example.backend.dto.InstructorDto;
import org.example.backend.entity.Department;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Role;
import org.example.backend.mapper.InstructorMapper;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    public InstructorService(InstructorRepository instructorRepository, InstructorMapper instructorMapper, DepartmentRepository departmentRepository, RoleRepository roleRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

    public Instructor insertInstructor(InstructorDto dto)
    {
        Instructor instructor=instructorMapper.mapperToInstructor(dto);
        Department department= departmentRepository.getGeneralDepartment(dto.getDepartmentName());
        System.out.println(department.getDepartmentId());
        instructor.setDepartment(department);

        Role role=roleRepository.getInstructorRole();
        instructor.getUser().addRole(role);
        return instructorRepository.save(instructor);
    }
}
