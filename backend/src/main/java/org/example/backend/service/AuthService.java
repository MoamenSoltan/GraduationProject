package org.example.backend.service;

import org.example.backend.config.JWt.JwtService;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.example.backend.mapper.InstructorMapper;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.util.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;


    public AuthService(AuthenticationManager manager, JwtService jwtService, UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.manager = manager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    public boolean isAdmin(UserDetails userDetails)
    {


            return userDetails.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

    public Authentication isAuthenticated(String email, String password)
    {
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            return authentication;
        } catch (BadCredentialsException e) {
            return null; // Return null if credentials are invalid
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    public AuthResponse login(String email, String password)
    {
        var auth=isAuthenticated(email,password);
        String token;
        if(auth.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            token=jwtService.generateToken(userDetails);
            User user = userRepository.getUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            Object userData = null;
            String message = "User login successful";

            if (isAdmin(userDetails)) {
                userData = user;
            } else {
                Student student = studentRepository.findByUser(user).orElse(null);
                if (student != null) {
                    userData = StudentMapper.toStudentResponseDTO(student);
                } else {
                    Instructor instructor = instructorRepository.findByUser(user).orElse(null);
                    if (instructor != null) {
                        userData = InstructorMapper.entityToResponseDTO(instructor);
                    } else {
                        throw new RuntimeException("User type not recognized");
                    }
                }
            }

            return new AuthResponse(token, message, userData);
        } else {
            throw new RuntimeException("Invalid credentials");
        }


    }
}
