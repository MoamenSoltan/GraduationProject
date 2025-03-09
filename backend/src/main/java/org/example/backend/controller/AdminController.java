package org.example.backend.controller;

import org.example.backend.dto.courseDto.CourseRequestDTO;
import org.example.backend.dto.courseDto.CourseResponseDTO;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.semesterDto.SemesterRequestDTO;
import org.example.backend.dto.semesterDto.SemesterResponseDTO;
import org.example.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final PasswordEncoder passwordEncoder;
    private final SemesterService semesterService;
    private final CourseService courseService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    public AdminController(StudentService studentService, InstructorService instructorService, PasswordEncoder passwordEncoder, SemesterService service, CourseService courseService, AdminService adminService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;

        this.semesterService = service;
        this.courseService = courseService;
        this.adminService = adminService;
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveSubmissionRequest(@PathVariable int id) {

        String approved = adminService.approveSubmissionRequest(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", approved);
        return ResponseEntity.ok(response);
    }


//    @GetMapping("/students")
//    public ResponseEntity<List<Student>> getAllStudent()
//    {
//        List<Student> students = studentService.getAllStudents();
//        return ResponseEntity.ok(students);
//    }

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

    @GetMapping("/{yearLevel}/{semesterName}")
    public SemesterResponseDTO getSemester(
            @PathVariable Integer yearLevel,
            @PathVariable String semesterName) {
        return semesterService.getSemesterById(yearLevel, semesterName);
    }
}
