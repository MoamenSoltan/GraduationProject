package org.example.backend.service;

import org.example.backend.entity.Role;
import org.example.backend.entity.Student;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.DepartmentName;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.RoleRepository;
import org.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubmissionRequestMapper requestMapper;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    public StudentService(StudentRepository studentRepository, SubmissionRequestMapper requestMapper, RoleRepository roleRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.requestMapper = requestMapper;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }

    public Student saveStudentAfterAcceptRequest(SubmissionRequest  request)
    {
        request.setAdmissionStatus(AdmissionStatus.ACCEPTED);
        Student student = requestMapper.mapToStudent(request);
        student.setCreatedAt(LocalDateTime.now());
        student.setDepartment(departmentRepository.getGeneralDepartment(DepartmentName.general));
        Role role = roleRepository.getStudentRole();
        student.getUser().addRole(role);

       return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
