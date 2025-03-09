package org.example.backend.service;

import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.entity.Department;
import org.example.backend.entity.Instructor;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.InstructorMapper;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorService {
    private static final Logger log = LoggerFactory.getLogger(InstructorService.class);
    private final InstructorRepository instructorRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    public InstructorService(InstructorRepository instructorRepository,
                             DepartmentRepository departmentRepository,
                             RoleRepository roleRepository) {
        this.instructorRepository = instructorRepository;

        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

//    @Transactional
//    public Instructor insertInstructor(InstructorDTO dto) {
//
//        Instructor instructor = instructorMapper.mapToInstructor(dto);
//        Department department = departmentRepository.getGeneralDepartment(dto.getDepartmentName())
//                .orElseThrow(() -> new ResourceNotFound("Department","department_name" , dto.getDepartmentName()));
//        instructor.setDepartment(department);
//
//        Role role = roleRepository.getInstructorRole()
//                .orElseThrow(() -> new ResourceNotFound("Role","role_id",2));
//        instructor.getUser().addRole(role);
//
//
//
//
//        Instructor instructor1=instructorRepository.save(instructor);
//        if(dto.isHeadOfDepartment())
//        {
//            department.setHeadOfDepartment(instructor1);
//        }
//
//        return instructor;
//    }


//    @Transactional
//    public String insertHeadOfDepartment(String email, DepartmentName departmentName)
//    {
//        Instructor instructor = instructorRepository.getByEmail(email);
//        Department department = departmentRepository.getGeneralDepartment(departmentName).get();
//
//        department.setHeadOfDepartment(instructor);
//        return "inserted";
//    }
    public InstructorResponseDTO createInstructor(InstructorRequestDTO requestDTO)
    {
        Instructor instructor = InstructorMapper.requestToEntity(requestDTO);

        Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFound("Department", "id", requestDTO.getDepartmentId()));

        instructor.setDepartment(department);
        if (requestDTO.getManagedDepartmentId() != null) {
            Department managedDepartment = departmentRepository.findById(requestDTO.getManagedDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Managed Department not found with id: " + requestDTO.getManagedDepartmentId()));
            instructor.setManagedDepartment(managedDepartment);
            managedDepartment.setHeadOfDepartment(instructor);
        }

        Instructor savedInstructor = instructorRepository.save(instructor);

        return InstructorMapper.entityToResponseDTO(savedInstructor);
    }

    public List<InstructorResponseDTO> getAllInstructors()
    {
        List<Instructor> instructors = instructorRepository.findAll();

        List<InstructorResponseDTO> responseDTOList = new ArrayList<>();

        for (Instructor instructor:instructors)
        {
            responseDTOList.add(InstructorMapper.entityToResponseDTO(instructor));
        }

        return responseDTOList;
    }
}