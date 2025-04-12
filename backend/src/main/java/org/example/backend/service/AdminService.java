package org.example.backend.service;

import org.example.backend.dto.MailBody;
import org.example.backend.entity.Role;
import org.example.backend.entity.Student;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.DepartmentName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.RoleRepository;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.SubmissionReqRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    private final SubmissionReqRepository submissionReqRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final MailService mailService;

    public AdminService(SubmissionReqRepository submissionReqRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository, StudentRepository studentRepository, MailService mailService) {
        this.submissionReqRepository = submissionReqRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.mailService = mailService;
    }

    public String approveSubmissionRequest(int id)
    {
        SubmissionRequest request = submissionReqRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("SubmissionRequest", "id", id));
        if(request.getAdmissionStatus()!= AdmissionStatus.PENDING)
            return "This request is already approved or rejected!";
        request.setAdmissionStatus(AdmissionStatus.ACCEPTED);

        Student student= SubmissionRequestMapper.mapToStudent(request);
        student.setCreatedAt(LocalDateTime.now());
        student.setDepartment(departmentRepository.getGeneralDepartment(DepartmentName.general).get());
        Role role = roleRepository.getStudentRole();
        student.getUser().addRole(role);
        studentRepository.save(student);


        mailService.sendEmail(createMail(request.getEmail(), "Accepted"));
        return "Submission request approved successfully!";
    }
    private MailBody createMail(String email,String status)
    {
        MailBody mailBody =new MailBody();
        mailBody.setTo(email);
        mailBody.setSubject("your submission is "+status);
        mailBody.setText("got to collage website \n" +
                "localhost:8080");

        return mailBody;
    }

    public String rejectSubmissionRequest(int id)
    {
        SubmissionRequest request = submissionReqRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("SubmissionRequest", "id", id));
        if(request.getAdmissionStatus()!= AdmissionStatus.PENDING)
            return "This request is already approved or rejected!";
        request.setAdmissionStatus(AdmissionStatus.REJECTED);
        submissionReqRepository.save(request);

        mailService.sendEmail(createMail(request.getEmail(), "Accepted"));
        return "Submission request rejected successfully!";
    }
}
