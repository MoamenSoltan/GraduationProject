package org.example.backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.dto.RefreshTokenRequest;
import org.example.backend.dto.submissionDto.SubmissionImages;
import org.example.backend.dto.submissionDto.SubmissionInfoRequestDTO;
import org.example.backend.service.AuthService;
import org.example.backend.util.AuthResponse;
import org.example.backend.util.LoginRequest;
import org.example.backend.service.SubmissionRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints related to user authentication and registration")
public class AuthController {
    private final SubmissionRequestService requestService;
   private final AuthService authService;


    public AuthController( SubmissionRequestService requestService, AuthService authService) {
        this.requestService = requestService;
        this.authService = authService;
    }



    @Operation(
            summary = "User login",
            description = "Authenticates a user with their email and password and returns a JWT token."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,HttpServletResponse servletResponse) {
        String email = request.getEmail();
        String password = request.getPassword();
        AuthResponse response=  authService.login(email,password,servletResponse);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "User registration",
            description = "Registers a new user with their personal information and images. " +
                    "The request should include form-data with 'info' as SubmissionInfoRequestDTO and 'images' as SubmissionImages."
    )
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


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response)
    {
        return ResponseEntity.ok(authService.refreshToken(request,response));
    }

   // @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {

        return ResponseEntity.ok(authService.getNewAccessToken(refreshToken.getRefreshToken()));
    }

    @GetMapping("/data")
//    @PostAuthorize("hasRole('STUDENT')")
    public String getData() {
        var a=SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : "+a.getName());
        return "data";
    }


}
