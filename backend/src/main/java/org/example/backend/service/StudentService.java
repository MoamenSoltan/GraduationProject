package org.example.backend.service;

import org.example.backend.dto.studentDto.StudentProfile;
import org.example.backend.dto.studentDto.StudentResponseDTO;
import org.example.backend.dto.studentDto.UpdateStudent;
import org.example.backend.entity.Role;
import org.example.backend.entity.Student;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.DepartmentName;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.RoleRepository;
import org.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubmissionRequestMapper requestMapper;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final FileService fileService;

    public StudentService(StudentRepository studentRepository, SubmissionRequestMapper requestMapper, RoleRepository roleRepository, DepartmentRepository departmentRepository, FileService fileService) {
        this.studentRepository = studentRepository;
        this.requestMapper = requestMapper;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.fileService = fileService;
    }

    public Student saveStudentAfterAcceptRequest(SubmissionRequest  request)
    {
        request.setAdmissionStatus(AdmissionStatus.ACCEPTED);
        Student student = requestMapper.mapToStudent(request);
        student.setCreatedAt(LocalDateTime.now());
        student.setDepartment(departmentRepository.getGeneralDepartment(DepartmentName.general).get());
        Role role = roleRepository.getStudentRole();
        student.getUser().addRole(role);

       return studentRepository.save(student);
    }

    public StudentProfile updateStudent(UpdateStudent updateStudent, Student student) throws IOException {
        if (updateStudent.getFirstName() != null) {
            student.getUser().setFirstName(updateStudent.getFirstName());
        }
        if (updateStudent.getLastName() != null) {
            student.getUser().setLastName(updateStudent.getLastName());
        }
        if (updateStudent.getCity() != null) {
            student.getSubmissionRequest().setCity(updateStudent.getCity());
        }
        if (updateStudent.getCountry() != null) {
            student.getSubmissionRequest().setCountry(updateStudent.getCountry());
        }
        if (updateStudent.getAddress() != null) {
            student.getSubmissionRequest().setAddress(updateStudent.getAddress());
        }
        if (updateStudent.getFirstName() != null) {
            student.getSubmissionRequest().setFirstName(updateStudent.getFirstName());
        }
        if (updateStudent.getLastName() != null) {
            student.getSubmissionRequest().setLastName(updateStudent.getLastName());
        }
        if (updateStudent.getPersonalImage() != null && !updateStudent.getPersonalImage().isEmpty()) {
            String fullPath = fileService.uploadFile(updateStudent.getPersonalImage());
            student.getSubmissionRequest().setPersonalPhoto(fullPath);
        }

        return StudentMapper.toStudentProfile(studentRepository.save(student));
    }

    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> responseDTOS=new ArrayList<>();
        for (Student student : students) {
            StudentResponseDTO responseDTO = StudentMapper.toStudentResponseDTO(student);
            responseDTOS.add(responseDTO);
        }

        return responseDTOS;
    }


//    public List<Student> getAllStudents() {
//        return studentRepository.findAll();
//    }
}
