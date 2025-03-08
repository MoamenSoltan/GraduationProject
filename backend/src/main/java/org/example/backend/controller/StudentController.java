package org.example.backend.controller;

import org.example.backend.dto.StudentCourseRequestDTO;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.StudentCoursesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentCoursesService studentCoursesService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public StudentController(StudentCoursesService studentCoursesService, UserRepository userRepository, StudentRepository studentRepository) {
        this.studentCoursesService = studentCoursesService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/course")
    ResponseEntity<?> signCourseForStudent(@RequestBody StudentCourseRequestDTO requestDTO)
    {
         studentCoursesService.enrollStudentInCourse(requestDTO);
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
        System.out.println("course code " +student.getStudentCourse().get(0).getCourse().getCourseCode());

        return ResponseEntity.ok(StudentMapper.toStudentProfile(student));
    }
}
