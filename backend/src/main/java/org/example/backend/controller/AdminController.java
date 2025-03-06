package org.example.backend.controller;

import org.example.backend.dto.*;
import org.example.backend.entity.Student;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.SubmissionReqRepository;
import org.example.backend.service.CourseService;
import org.example.backend.service.InstructorService;
import org.example.backend.service.SemesterService;
import org.example.backend.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final SemesterService semesterService;
    private final CourseService courseService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(SubmissionReqRepository reqRepository, StudentService studentService, InstructorService instructorService, PasswordEncoder passwordEncoder, SemesterService service, CourseService courseService) {
        this.reqRepository = reqRepository;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;

        this.semesterService = service;
        this.courseService = courseService;
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
    public ResponseEntity<InstructorResponseDTO> addInstructor(@RequestBody InstructorRequestDTO instructorDto)
    {
        instructorDto.setPassword(passwordEncoder.encode(instructorDto.getPassword()));

        InstructorResponseDTO responseDto = instructorService.createInstructor(instructorDto);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<InstructorResponseDTO>> getAllInstructors()
    {
        List<InstructorResponseDTO> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getUserAuth(Principal principal){
        var auth = SecurityContextHolder.getContext().getAuthentication();
//        HttpServletRequest request
//        var user = request.getUserPrincipal();

        String response = "User: " + principal.getName() + ", Role: " + auth.getAuthorities();
//        authentication.getDetails()
        return ResponseEntity.ok(response);
    }

    @PostMapping("/semester")
    public ResponseEntity<SemesterResponseDTO> createSemester(@RequestBody SemesterRequestDTO dto) {
        logger.info("Received request payload: {}", dto);
        SemesterResponseDTO response = semesterService.createSemester(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/semester")
    public ResponseEntity<List<SemesterResponseDTO>> getAllSemesters() {
        List<SemesterResponseDTO> semesters = semesterService.getAllSemesters();
        return ResponseEntity.ok(semesters);
    }

    @PostMapping("/course")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO dto) {
        CourseResponseDTO response = courseService.createCourse(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/course")
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<CourseResponseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
}
