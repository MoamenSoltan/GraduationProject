package org.example.backend.controller;

import org.example.backend.dto.AnnouncementDto.AnnouncementRequestDTO;
import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.instructorDto.UpdateInstructor;
import org.example.backend.entity.Instructor;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.service.AnnouncementService;
import org.example.backend.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorRepository instructorRepository;
    private final AnnouncementService announcementService;
    private final InstructorService instructorService;

    public InstructorController(InstructorRepository instructorRepository, AnnouncementService announcementService, InstructorService instructorService) {
        this.instructorRepository = instructorRepository;
        this.announcementService = announcementService;
        this.instructorService = instructorService;
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
}
