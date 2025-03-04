package org.example.backend.service;

import org.example.backend.dto.InstructorDTO;
import org.example.backend.entity.Department;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Role;
import org.example.backend.enums.DepartmentName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.InstructorMapper;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstructorService {
    private static final Logger log = LoggerFactory.getLogger(InstructorService.class);
    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    public InstructorService(InstructorRepository instructorRepository,
                             InstructorMapper instructorMapper,
                             DepartmentRepository departmentRepository,
                             RoleRepository roleRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Instructor insertInstructor(InstructorDTO dto) {

        Instructor instructor = instructorMapper.mapToInstructor(dto);
        Department department = departmentRepository.getGeneralDepartment(dto.getDepartmentName())
                .orElseThrow(() -> new ResourceNotFound("Department","department_name" , dto.getDepartmentName()));
        instructor.setDepartment(department);

        Role role = roleRepository.getInstructorRole()
                .orElseThrow(() -> new ResourceNotFound("Role","role_id",2));
        instructor.getUser().addRole(role);




        Instructor instructor1=instructorRepository.save(instructor);
        if(dto.isHeadOfDepartment())
        {
            department.setHeadOfDepartment(instructor1);
        }

        return instructor;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Transactional
    public String insertHeadOfDepartment(String email, DepartmentName departmentName)
    {
        Instructor instructor = instructorRepository.getByEmail(email);
        Department department = departmentRepository.getGeneralDepartment(departmentName).get();

        department.setHeadOfDepartment(instructor);
        return "inserted";
    }
}