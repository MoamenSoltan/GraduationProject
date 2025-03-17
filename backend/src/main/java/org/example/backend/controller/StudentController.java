package org.example.backend.controller;

import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.dto.StudentCourseRequestDTO;
import org.example.backend.dto.studentDto.UpdateStudent;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.example.backend.enums.AnnouncementType;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.AnnouncementService;
import org.example.backend.service.InstructorService;
import org.example.backend.service.StudentCoursesService;
import org.example.backend.service.StudentService;
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

    public StudentController(StudentCoursesService studentCoursesService, UserRepository userRepository, StudentRepository studentRepository, AnnouncementService announcementService, StudentService studentService, InstructorService instructorService) {
        this.studentCoursesService = studentCoursesService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.announcementService = announcementService;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @PostMapping("/course")
    ResponseEntity<?> signCourseForStudent(@RequestBody StudentCourseRequestDTO requestDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student= studentRepository.findStudentByEmail(authentication.getName())
                .orElseThrow(()-> new ResourceNotFound("Student", "email", authentication.getName()));
         studentCoursesService.enrollStudentInCourse(requestDTO,student);
         return ResponseEntity.ok().build();
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

        return ResponseEntity.ok(StudentMapper.toStudentProfile(student));
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateStudent(@ModelAttribute UpdateStudent updateStudent) throws IOException {
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFound("User","email",auth.getName()));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentService.updateStudent(updateStudent,student);
        return ResponseEntity.ok("updated");
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncements(
            @RequestParam(name = "type", required = false) AnnouncementType type)
    {
        List<AnnouncementResponseDTO> announcements = announcementService.getAnnouncementsByType(type);
        return ResponseEntity.ok(announcements);
    }
    @GetMapping("/instructors")
    public ResponseEntity<?> getInstructorOfCoursesRegistered()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        return ResponseEntity.ok(instructorService.getCoursesInstructorsForStudent(email));
    }
}
