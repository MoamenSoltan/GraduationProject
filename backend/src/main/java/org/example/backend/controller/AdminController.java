package org.example.backend.controller;

import jakarta.validation.Valid;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.courseDto.CourseRequestDTO;
import org.example.backend.dto.courseDto.CourseResponseDTO;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.semesterDto.SemesterRequestDTO;
import org.example.backend.dto.semesterDto.SemesterResponseDTO;
import org.example.backend.entity.*;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.SemesterName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.*;
import org.example.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private final SubmissionRequestService submissionRequestService;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public AdminController(StudentService studentService, InstructorService instructorService, PasswordEncoder passwordEncoder, SemesterService service, CourseService courseService, AdminService adminService, SubmissionRequestService submissionRequestService, InstructorRepository instructorRepository, CourseRepository courseRepository, DepartmentRepository departmentRepository, UserRepository userRepository, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;

        this.semesterService = service;
        this.courseService = courseService;
        this.adminService = adminService;
        this.submissionRequestService = submissionRequestService;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveSubmissionRequest(@PathVariable int id) {

        String approved = adminService.approveSubmissionRequest(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", approved);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectSubmissionRequest(@PathVariable int id)
    {
        Map<String ,String > response = new HashMap<>();
        response.put("message", adminService.rejectSubmissionRequest(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/submissions")
    public ResponseEntity<?> getAllSubmissions(@RequestParam(name = "status",required = false) AdmissionStatus status)
    {
        return ResponseEntity.ok(submissionRequestService.getAllSubmissions(status));
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

    @DeleteMapping("/instructor/{id}")
    @Transactional
    public ResponseEntity<?> deleteInstructor(@PathVariable int id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Instructor", "id", id));

        User user = instructor.getUser();
        if (user != null) {
            //  Remove user from user_roles
            instructorRepository.deleteByUserId(user.getId());


            userRepository.delete(user);
        }


        instructorRepository.delete(instructor);

        return ResponseEntity.ok("Instructor deleted successfully");
    }

    @DeleteMapping("/student/{id}")
    @Transactional
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        Student student = studentRepository.findById((long) id)
                .orElseThrow(() -> new ResourceNotFound("Instructor", "id", id));

        User user = student.getUser();
        if (user != null) {
            //  Remove user from user_roles
            instructorRepository.deleteByUserId(user.getId());


            userRepository.delete(user);
        }

//        List<StudentCourse> course=student.getStudentCourse();


        studentRepository.delete(student);

        return ResponseEntity.ok("Student deleted successfully");
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

    @DeleteMapping("/course/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        String message = courseService.deleteCourse(id);
        return ResponseEntity.ok(message);
    }
    @PutMapping("/course/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id,
                                                          @Valid @RequestBody CourseRequestDTO courseDTO) {
        CourseResponseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    public ResponseEntity<?> updateCourse(@Valid @RequestBody CourseRequestDTO courseDTO)
    {
        courseService.updateCourse(courseDTO);
        return ResponseEntity.ok("course updated successfully");
    }
    @GetMapping("/{yearLevel}/{semesterName}")
    public SemesterResponseDTO getSemester(
            @PathVariable Integer yearLevel,
            @PathVariable String semesterName) {
        return semesterService.getSemesterById(yearLevel, semesterName);
    }

    @PostMapping("/{yearLevel}/{semesterName}/{courseId}")
    public ResponseEntity<?> assignCoursesToSemester(
            @PathVariable Integer yearLevel,
            @PathVariable SemesterName semesterName,
            @PathVariable Long courseId
    )
    {
        System.out.println("Received Request: Assign Course " + courseId + " to Semester " + yearLevel + " - " + semesterName);
        CourseResponseDTO updatedCourse = courseService.assignCourseToSemester(yearLevel, semesterName, courseId);
        System.out.println(updatedCourse.getCourseCode());


        return ResponseEntity.ok(updatedCourse);
    }
}
