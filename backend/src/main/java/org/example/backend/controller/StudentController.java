package org.example.backend.controller;

import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.dto.StudentCourseRequestDTO;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.example.backend.enums.AnnouncementType;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.AnnouncementService;
import org.example.backend.service.StudentCoursesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentCoursesService studentCoursesService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AnnouncementService announcementService;

    public StudentController(StudentCoursesService studentCoursesService, UserRepository userRepository, StudentRepository studentRepository, AnnouncementService announcementService) {
        this.studentCoursesService = studentCoursesService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.announcementService = announcementService;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));
      //  System.out.println("course code " +student.getStudentCourse().get(0).getCourse().getCourseCode());

        return ResponseEntity.ok(StudentMapper.toStudentProfile(student));
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncements(
            @RequestParam(name = "type", required = false) AnnouncementType type)
    {
        List<AnnouncementResponseDTO> announcements = announcementService.getAnnouncementsByType(type);
        return ResponseEntity.ok(announcements);
    }
}
