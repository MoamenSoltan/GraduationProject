package org.example.backend.controller;

import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.dto.StudentCourseRequestDTO;
import org.example.backend.dto.courseDto.CourseResponseDTO;
import org.example.backend.dto.courseDto.DegreeCourseDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.studentDto.StudentProfile;
import org.example.backend.dto.studentDto.UpdateStudent;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.example.backend.enums.AnnouncementType;
import org.example.backend.enums.LevelYear;
import org.example.backend.enums.SemesterName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentCoursesService studentCoursesService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AnnouncementService announcementService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;

    public StudentController(StudentCoursesService studentCoursesService, UserRepository userRepository, StudentRepository studentRepository, AnnouncementService announcementService, StudentService studentService, InstructorService instructorService, CourseService courseService) {
        this.studentCoursesService = studentCoursesService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.announcementService = announcementService;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @PostMapping("/course")
    ResponseEntity<String> signCourseForStudent(@RequestBody List<StudentCourseRequestDTO> requestDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student= studentRepository.findStudentByEmail(authentication.getName())
                .orElseThrow(()-> new ResourceNotFound("Student", "email", authentication.getName()));

//        for (var dto:requestDTO)
//        {
//            System.out.println("course id :" +dto.getCourseId());
//        }
         String response=studentCoursesService.enrollStudentInCourse(requestDTO,student);
         return ResponseEntity.ok(response);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getStudent()
    {
        var auth= SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFound("User","email",auth.getName()));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));
      //  System.out.println("course code " +student.getStudentCourse().get(0).getCourse().getCourseCode());

        System.out.println("profile image :  dhbd: "+student.getSubmissionRequest().getPersonalPhoto());
        return ResponseEntity.ok(StudentMapper.toStudentProfile(student));
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateStudent(@ModelAttribute UpdateStudent updateStudent) throws IOException {
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFound("User","email",auth.getName()));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        StudentProfile profile = studentService.updateStudent(updateStudent,student);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncements(
            @RequestParam(name = "type", required = false) AnnouncementType type)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findStudentByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFound("Student", "email", authentication.getName()));
        
        List<AnnouncementResponseDTO> announcements;
        if (type != null) {
            announcements = announcementService.getAnnouncementsForStudentByType(student, type);
        } else {
            announcements = announcementService.getAnnouncementsForStudent(student);
        }
        return ResponseEntity.ok(announcements);
    }
    @GetMapping("/instructors")
    public ResponseEntity<List<InstructorResponseDTO>> getInstructorOfCoursesRegistered()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        List<InstructorResponseDTO> responseDTOS= instructorService.getCoursesInstructorsForStudent(email);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getCoursesForRegistration()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
//        System.out.println("email : "+email);
        List<CourseResponseDTO> courses =courseService.getCoursesForRegistration(email);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/signed")
    public ResponseEntity<?> getCoursesSignedByStudent(@RequestParam(name = "year",required = false)LevelYear year)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();
       List<CourseResponseDTO> responseDTOS= courseService.getCoursesCompletedByStudent(studentEmail, year);
        return ResponseEntity.ok(responseDTOS);
    }




    @GetMapping("/semesters/degree")
    public ResponseEntity<List<DegreeCourseDTO>> getAllSemestersForStudent(
            @RequestParam(name = "semesterName", required = false) SemesterName semesterName,
            @RequestParam(name = "semesterYear", required = false) Integer semesterYear) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();
        List<DegreeCourseDTO> obj = studentCoursesService.getCoursesWithDegreeByStudentAndSemester(studentEmail, semesterName, semesterYear);
        return ResponseEntity.ok(obj);
    }

}
