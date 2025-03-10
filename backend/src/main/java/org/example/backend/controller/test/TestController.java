package org.example.backend.controller.test;

import org.example.backend.dto.submissionDto.SubmissionImages;
import org.example.backend.dto.submissionDto.SubmissionInfoRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/sendfile")
    public ResponseEntity<?> sendFile(
            @ModelAttribute("info") SubmissionInfoRequestDTO info,
            @ModelAttribute("images") SubmissionImages images
    ) throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        // Debug logging
        System.out.println("Info: " + info);
        System.out.println(" - firstName: " + info.getFirstName());
        System.out.println(" - lastName: " + info.getLastName());
        System.out.println(" - email: " + info.getEmail());
        System.out.println("Images: " + images);
        if (images != null) {
            System.out.println(" - idPhoto: " + (images.getIdPhoto() != null ? images.getIdPhoto().getOriginalFilename() : "null"));
            System.out.println(" - highSchoolCertificate: " + (images.getHighSchoolCertificate() != null ? images.getHighSchoolCertificate().getOriginalFilename() : "null"));
            System.out.println(" - personalPhoto: " + (images.getPersonalPhoto() != null ? images.getPersonalPhoto().getOriginalFilename() : "null"));
        }

        Map<String, String> response = new HashMap<>();
        String username = (info.getFirstName() != null ? info.getFirstName() : "null") + " " +
                (info.getLastName() != null ? info.getLastName() : "null");
        response.put("username", username);

        // Base URL for serving images
        String baseUrl = "http://localhost:8080/api/images/";

        // Handle and save images
        if (images != null) {
            if (images.getIdPhoto() != null) {
                String fileName = UUID.randomUUID() + "_" + images.getIdPhoto().getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, images.getIdPhoto().getBytes());
                response.put("idPhoto", baseUrl + fileName);
            } else {
                response.put("idPhoto", "No ID Photo Uploaded");
            }

            if (images.getHighSchoolCertificate() != null) {
                String fileName = UUID.randomUUID() + "_" + images.getHighSchoolCertificate().getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, images.getHighSchoolCertificate().getBytes());
                response.put("highSchoolCertificate", baseUrl + fileName);
            } else {
                response.put("highSchoolCertificate", "No Certificate Uploaded");
            }

            if (images.getPersonalPhoto() != null) {
                String fileName = UUID.randomUUID() + "_" + images.getPersonalPhoto().getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, images.getPersonalPhoto().getBytes());
                response.put("personalPhoto", baseUrl + fileName);
            } else {
                response.put("personalPhoto", "No Personal Photo Uploaded");
            }
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public String get(
            @ModelAttribute("info") SubmissionInfoRequestDTO info,
            @ModelAttribute("images") SubmissionImages images
    ){


        return info.getFirstName();
    }


    @GetMapping("/test")
    public ResponseEntity<String> getUserAuth(Principal principal){
        var auth = SecurityContextHolder.getContext().getAuthentication();
//        HttpServletRequest request
//        var user = request.getUserPrincipal();

        String response = "User: " + principal.getName() + ", Role: " + auth.getAuthorities();
//        authentication.getDetails()
        return ResponseEntity.ok(response);
    }
}
