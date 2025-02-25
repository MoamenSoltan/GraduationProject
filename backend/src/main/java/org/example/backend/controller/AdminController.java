package org.example.backend.controller;

import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.repository.SubmissionReqRepository;
import org.example.backend.service.UserService;
import org.example.backend.util.AdmissionStatus;
import org.example.backend.util.GenderType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final SubmissionReqRepository submissionReqRepository;
    private final UserService userService;

    public AdminController(SubmissionReqRepository submissionReqRepository, UserService userService) {
        this.submissionReqRepository = submissionReqRepository;
        this.userService = userService;
    }

    @PostMapping("/approve/{id}")
    public String approveSubmissionRequest(@PathVariable int id)
    {
        SubmissionRequest request =submissionReqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setAdmissionStatus(AdmissionStatus.ACCEPTED);
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLastName(request.getLastName());
        user.setSubmissionRequest(request);
        user.setPassword(request.getPassword());
        user.setGender(GenderType.MALE);
        user.setFirstName(request.getFirstName());

        userService.save(user);

        var auth= new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);


        return "user updated successfully";

    }
}
