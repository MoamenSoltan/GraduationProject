package org.example.backend.controller;

import org.example.backend.config.JWt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    @Value("${file.upload}")
    private  String UPLOAD_DIR ; // Use a correct relative path
    private final JwtService jwtService;

    public FileController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename,@CookieValue(name = "accessToken", required = false) String token) {
//        System.out.println("Received token: " + token);
//        if(token == null || token.isEmpty()) {
//            return ResponseEntity.status(401).build();
//        }

//        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(401).build();
//        }

        // Extract the token from the header
//        String token2 = authHeader.substring(7);
        try {
            jwtService.isValidateToken(token);
            Path file = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }


            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
