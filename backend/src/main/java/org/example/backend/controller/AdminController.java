package org.example.backend.controller;

import org.example.backend.dto.InstructorDto;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Student;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.SubmissionReqRepository;
import org.example.backend.service.InstructorService;
import org.example.backend.service.StudentService;
import org.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final SubmissionReqRepository reqRepository;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(SubmissionReqRepository reqRepository, StudentService studentService, InstructorService instructorService, PasswordEncoder passwordEncoder) {
        this.reqRepository = reqRepository;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;
    }


//    @PostMapping("/approve/{id}")
//    public ResponseEntity<?> approveSubmissionRequest(@PathVariable int id)
//    {
//        SubmissionRequest request = reqRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFound("request submission","id",id));
//
//        studentService.approveStudent(request);
//
////        var authentication =SecurityContextHolder.getContext().getAuthentication();
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
//            authentication = authenticationManager.authenticate(authToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Submission request approved successfully");
//        return ResponseEntity.ok(response);
//    }
//
    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveSubmissionRequest(@PathVariable int id) {

        // add role
        // add status
        SubmissionRequest request = reqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("request submission", "id", id));

        Student student = studentService.saveStudentAfterAcceptRequest(request);

        User user =student.getUser();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Submission request approved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudent()
    {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/instructor")
    public ResponseEntity<Instructor> addInstructor(@RequestBody InstructorDto instructorDto)
    {
        instructorDto.setPassword(passwordEncoder.encode(instructorDto.getPassword()));

        Instructor instructor = instructorService.insertInstructor(instructorDto);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(instructor);
    }
}
