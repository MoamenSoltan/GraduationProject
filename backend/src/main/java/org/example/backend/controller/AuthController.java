package org.example.backend.controller;


import org.example.backend.dto.SubmissionImages;
import org.example.backend.dto.SubmissionInfoRequestDTO;
import org.example.backend.service.AuthService;
import org.example.backend.util.AuthResponse;
import org.example.backend.util.LoginRequest;
import org.example.backend.service.SubmissionRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final SubmissionRequestService requestService;
   private final AuthService authService;


    public AuthController( SubmissionRequestService requestService, AuthService authService) {
        this.requestService = requestService;
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        AuthResponse response=  authService.login(email,password);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @ModelAttribute("info") SubmissionInfoRequestDTO info,
            @ModelAttribute("images") SubmissionImages images
    )
    {
        String saved;
        try {
            saved= requestService.save(info,images);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/data")
//    @PostAuthorize("hasRole('STUDENT')")
    public String getData() {
        var a=SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : "+a.getName());
        return "data";
    }


}
