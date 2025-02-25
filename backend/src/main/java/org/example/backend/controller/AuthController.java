package org.example.backend.controller;



import org.example.backend.config.JWt.JwtService;
import org.example.backend.dto.LoginRequest;
import org.example.backend.dto.UserDto;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.SubmissionRequestService;
import org.example.backend.service.UserService;
import org.example.backend.util.AdmissionStatus;
import org.example.backend.util.RoleType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public String loginOrRegister(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return "login successful : "+jwtService.generateToken(userDetails);
    }

    @PostMapping("/register")
    public String register(@RequestBody SubmissionRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setUserType(RoleType.STUDENT);
        request.setAcademicYear(1);
        request.setAdmissionStatus(AdmissionStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        requestService.saveSubmissionRequest(request);

        // when admin approval the submission
        // var auth =new UserPasswordAuthenticationToken(saveUser.getemail(),password);
        //SecurityContextHolder.getContext().setAuthentication(auth);
        // return authresponse with token information
        return "sign completed";
    }

    @GetMapping("/data")
//    @PostAuthorize("hasRole('STUDENT')")
    public String getData() {
        var a=SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : "+a.getName());
        return "data";
    }
}
