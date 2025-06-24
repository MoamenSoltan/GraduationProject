package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.courseDto.CourseRequestDTO;
import org.example.backend.dto.courseDto.CourseResponseDTO;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.semesterDto.SemesterRequestDTO;
import org.example.backend.dto.semesterDto.SemesterResponseDTO;
import org.example.backend.dto.studentDto.StudentResponseDTO;
import org.example.backend.dto.submissionDto.SubmissionResponseDTO;
import org.example.backend.entity.*;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.SemesterName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.*;
import org.example.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

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
    private final PromotionService promotionService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final FileService fileService;

    public AdminController(StudentService studentService, InstructorService instructorService, PasswordEncoder passwordEncoder, SemesterService service, CourseService courseService, AdminService adminService, SubmissionRequestService submissionRequestService, InstructorRepository instructorRepository, PromotionService promotionService, UserRepository userRepository, StudentRepository studentRepository, FileService fileService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;

        this.semesterService = service;
        this.courseService = courseService;
        this.adminService = adminService;
        this.submissionRequestService = submissionRequestService;
        this.instructorRepository = instructorRepository;
        this.promotionService = promotionService;

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.fileService = fileService;
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveSubmissionRequest(@PathVariable int id) {

        Student approved = adminService.approveSubmissionRequest(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Submission request approved successfully!");

        sendDataToBubble(approved);
        return ResponseEntity.ok(response);
    }
    private String sendDataToBubble(Student student)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://profiletrackerai.bubbleapps.io/version-test/api/1.1/wf/insert_user";
        String addurl= UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("email",student.getUser().getEmail())
                .queryParam("password", student.getUser().getPassword())
                .queryParam("name",student.getUser().getFirstName()+" "+student.getUser().getLastName())
                .toUriString();

        return restTemplate.getForObject(addurl,  String.class);
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

    @GetMapping("/pagination/submissions")
    public ResponseEntity<List<SubmissionResponseDTO>> getAllSubmissions(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir // Added sort direction
    ) {
        return ResponseEntity.ok(submissionRequestService.getAllSubmissions(pageNumber, pageSize, sortBy, sortDir));
    }


    @GetMapping("/submissions/sort/{field}")
    ResponseEntity<List<SubmissionResponseDTO>> getSubmissionsSortedByField(@PathVariable String field)
    {
        return ResponseEntity.ok(submissionRequestService.getSubmissionsWithSorting(field));
    }
    @GetMapping("/submissions/{id}")
    public ResponseEntity<SubmissionResponseDTO> getSubmissionByID(@PathVariable int id)
    {
        return ResponseEntity.ok(submissionRequestService.getSubmissionById(id));
    }

    @GetMapping("/submissions/page")
    public ResponseEntity<List<SubmissionRequest>> getAllSubmissionsWithPagination(@RequestParam(defaultValue = "0") int offset,
                                                                                       @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(submissionRequestService.getSubmissionWithPagination(offset, size));
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

    @GetMapping("/instructor/managed")
    public ResponseEntity<List<InstructorResponseDTO>> getInstructorMangesDp()
    {
        List<InstructorResponseDTO> instructorManges = instructorService.getInstructManagesDp();
        return ResponseEntity.ok(instructorManges);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<InstructorResponseDTO> getInstructorById(@PathVariable int id)
    {
        InstructorResponseDTO instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructor);
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

    @GetMapping("/student")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents()
    {

        return ResponseEntity.ok(studentService.getAllStudents());
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
    @GetMapping("/course/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
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
    @GetMapping("/student/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
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

    @Operation(
            summary = "Get all students in a course by course id and download as csv file",
            description = "This endpoint retrieves all students in a course by course id and downloads the data as a CSV file."
    )
    @GetMapping("/course/{courseId}/students/download")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable Long courseId) {
        List<Student> students = courseService.getStudentsByCourseId(courseId);
        if (students.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
       ByteArrayInputStream csvData = fileService.loadStudentForAdmin(students);

        HttpHeaders headers=new HttpHeaders();

        String filename = "student_"+students.get(0).getStudentCourse().get(0).getCourse().getCourseCode()+".csv";
        headers.add("Content-Disposition", "attachment; filename="+filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csvData));
//        return ResponseEntity.ok(students);
    }

    @PostMapping("/promote-students")
    public ResponseEntity<?> promoteAllStudents() {
        List<Student> allStudents = studentRepository.findAll();
        List<Long> studentIds = allStudents.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toList());
        promotionService.checkAndPromoteMultipleStudents(studentIds);

        return ResponseEntity.ok("Student promotion process completed successfully");
    }
}
