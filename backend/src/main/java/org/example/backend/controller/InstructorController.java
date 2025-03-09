package org.example.backend.controller;

import org.example.backend.dto.AnnouncementDto.AnnouncementRequestDTO;
import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.entity.Instructor;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorRepository instructorRepository;
    private final AnnouncementService announcementService;

    public InstructorController(InstructorRepository instructorRepository, AnnouncementService announcementService) {
        this.instructorRepository = instructorRepository;
        this.announcementService = announcementService;
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
}
