package org.example.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.config.JWt.JwtService;
import org.example.backend.dto.SubmissionRequestDto;
import org.example.backend.util.AuthResponse;
import org.example.backend.util.LoginRequest;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.service.SubmissionRequestService;
import org.example.backend.service.UserService;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.RoleType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SubmissionRequestService requestService;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, SubmissionRequestService requestService, AuthenticationManager manager, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.requestService = requestService;
        this.manager = manager;
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();



            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            String message = "user login successful";
            return new ResponseEntity<>(new AuthResponse(token, message), HttpStatus.ACCEPTED);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register( @RequestPart("submissionRequestDto") String jsonDto,
                                       @RequestPart("idPhoto") MultipartFile idPhoto,
                                       @RequestPart("personalPhoto") MultipartFile personalPhoto,
                                       @RequestPart("highSchoolCertificate") MultipartFile highSchoolCertificate) throws IOException {

        ObjectMapper objectMapper=new ObjectMapper();
        SubmissionRequestDto dto = objectMapper.readValue(jsonDto, SubmissionRequestDto.class);
        System.out.println(dto);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setIdPhoto(idPhoto);
        dto.setPersonalPhoto(personalPhoto);
        dto.setHighSchoolCertificate(highSchoolCertificate);
        SubmissionRequest request =requestService.saveSubmissionRequest(dto);

        System.out.println(dto.getPassword());
        // when admin approval the submission
        // var auth =new UserPasswordAuthenticationToken(saveUser.getemail(),password);
        //SecurityContextHolder.getContext().setAuthentication(auth);
        // return authresponse with token information

        System.out.println("saved submission");
        return ResponseEntity.ok(request);


    }

    @GetMapping("/data")
//    @PostAuthorize("hasRole('STUDENT')")
    public String getData() {
        var a=SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : "+a.getName());
        return "data";
    }


}
