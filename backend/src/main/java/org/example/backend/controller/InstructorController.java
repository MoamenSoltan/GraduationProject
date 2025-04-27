package org.example.backend.controller;

import org.example.backend.dto.AnnouncementDto.AnnouncementRequestDTO;
import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.instructorDto.UpdateInstructor;
import org.example.backend.dto.studentDto.StudentCourseDTO;
import org.example.backend.dto.studentDto.StudentCourseGradeDTO;
import org.example.backend.entity.Instructor;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.service.AnnouncementService;
import org.example.backend.service.FileService;
import org.example.backend.service.InstructorService;
import org.example.backend.util.CurrentUser;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorRepository instructorRepository;
    private final AnnouncementService announcementService;
    private final InstructorService instructorService;
    private final FileService fileService;

    public InstructorController(InstructorRepository instructorRepository, AnnouncementService announcementService, InstructorService instructorService, FileService fileService) {
        this.instructorRepository = instructorRepository;
        this.announcementService = announcementService;
        this.instructorService = instructorService;
        this.fileService = fileService;
    }

    @PostMapping("/announcement")
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementRequestDTO requestDTO)
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails= (UserDetails) auth.getDetails();
        String email = auth.getName();
        Instructor instructor=
                instructorRepository.getByEmail(email)
                        .orElseThrow(()-> new ResourceNotFound("Instructor","email",email));

        AnnouncementResponseDTO response =announcementService.createAnnouncement(requestDTO,instructor);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/announcement")
    public ResponseEntity<?> getAnnouncements()
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Instructor instructor=
                instructorRepository.getByEmail(email)
                        .orElseThrow(()-> new ResourceNotFound("Instructor","email",email));

        return ResponseEntity.ok(announcementService.getAnnouncements(instructor));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile()
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        System.out.println(email);

        return ResponseEntity.ok(instructorService.getInstructorProfile(email));
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@ModelAttribute UpdateInstructor updateInstructor) throws IOException {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Instructor instructor=instructorRepository.getByEmail(email)
                .orElseThrow(()->new ResourceNotFound("Instructor", "email",email));

        String message = instructorService.UpdateInstructor(updateInstructor,instructor);
        return ResponseEntity.ok(message);

    }

    @GetMapping("/course")
    public  ResponseEntity<List<CourseDTO>> getInstructorCourses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();


        return ResponseEntity.ok(instructorService.getInstructorCourses(email));
    }
    @GetMapping("/course/{courseId}/student")
    public ResponseEntity<?> getStudentsInCourse(@PathVariable int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return ResponseEntity.ok(instructorService.getStudentsInCourse(courseId,email));
    }

    @GetMapping("/course/{courseId}/student/{studentId}")
    public ResponseEntity<StudentCourseDTO> getStudentInCourse(@PathVariable int courseId ,
                                                                @PathVariable Long studentId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return ResponseEntity.ok(instructorService.getStudentInCourse(courseId,email,studentId));
    }

    @GetMapping("/course/{courseId}/student/download")
    public ResponseEntity<?> downloadStudentsInCourse(@PathVariable int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<StudentCourseDTO> students = instructorService.getStudentsInCourse(courseId, email);
        ByteArrayInputStream csvData = fileService.load(students);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.csv");

        // Return the CSV data as a downloadable resource
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csvData));
    }

    @PutMapping("/course/{courseId}/student/degree/upload")
    public ResponseEntity<?> updateStudentDegree(@PathVariable int courseId ,@RequestParam("file") MultipartFile fileCSV)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        String message=instructorService.updateStudentDegree(courseId,fileCSV,email);



        return ResponseEntity.ok("File uploaded successfully: " + message);
    }

    @GetMapping("/course/{courseId}/student/result/download")
    public ResponseEntity<?> downloadStudentsInCourseWithFinalDegrees(
            @PathVariable int courseId,
            @CurrentUser String email
    ) {

        List<StudentCourseGradeDTO> students = instructorService.getStudentsWithFinalDegree((long) courseId, email);
        String courseName=students.get(0).getCourseName();
        ByteArrayInputStream csvData = fileService.loadAllDegrees(students);


        HttpHeaders headers = new HttpHeaders();
        String fileName="students_degrees_course_"+courseName+".csv";
        headers.add("Content-Disposition", "attachment; filename="+fileName);

        // Return the CSV data as a downloadable resource
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csvData));


    }

}
