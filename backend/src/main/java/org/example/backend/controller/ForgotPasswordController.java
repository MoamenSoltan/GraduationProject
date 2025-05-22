package org.example.backend.controller;

import org.example.backend.dto.EmailVerification;
import org.example.backend.dto.MailBody;
import org.example.backend.dto.OtpDTO;
import org.example.backend.dto.ResetPasswordDTO;
import org.example.backend.entity.ForgotPassword;
import org.example.backend.entity.User;
import org.example.backend.repository.ForgotPasswordRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(UserRepository userRepository, MailService mailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // send email for email confirmation
    @PostMapping("/verify-email/")
    public ResponseEntity<String> sendEmailVerification(@RequestBody EmailVerification obj) {
        // Get the user by email
        String email=obj.getEmail();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        // Check if there is already an active OTP for this user
        ForgotPassword existingOtp = forgotPasswordRepository.findByUserAndExpirationTimeAfter(user, new Date())
                .orElse(null);
       // System.out.println("OTP ID "+existingOtp.getFpId());

        if (existingOtp != null) {
            // If an OTP exists and hasn't expired, return a message indicating the user can only request one OTP at a time
            throw new RuntimeException("You already have a pending OTP. Please try again later.");
        }

        // Generate new OTP
        int otp = otpGenerator();

        // Create the mail body
        MailBody mailBody = new MailBody();
        mailBody.setTo(email);
        mailBody.setSubject("OTP for Forgot Password");
        mailBody.setText("This is the OTP for your Forgot Password: " + otp);

        // Create ForgotPassword entity and set the expiration time
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000)); // OTP expires in 70 seconds
        forgotPassword.setOtp(otp);
        forgotPassword.setUser(user);

        // Send email with OTP
        mailService.sendEmail(mailBody);

        // Save the new OTP to the database
        forgotPasswordRepository.save(forgotPassword);

        return ResponseEntity.ok("Email sent successfully for verification");
    }


    @PostMapping("/verify-otp/")
    public ResponseEntity<String> verifyOtp(
            @RequestBody OtpDTO dto
            )
    {
        String email= dto.getEmail();
        Integer otp = dto.getOtp();

        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("pleas provide an valid email "));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp,user)
                .orElseThrow(()-> new RuntimeException("invalid OTP for email : " + email));

        if(forgotPassword.getExpirationTime().before(Date.from(Instant.now())))
        {
            forgotPasswordRepository.deleteById(forgotPassword.getFpId());
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP is valid");
    }

    // reset password
    @PostMapping("/reset-password/")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordDTO passwordDTO

            )
    {
        String email=passwordDTO.getEmail();
        if(!Objects.equals(passwordDTO.getPassword(),passwordDTO.getConfirmPassword()))
        {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.EXPECTATION_FAILED);
        }

       String encodePassword = passwordEncoder.encode(passwordDTO.getPassword());
        userRepository.updatePassword(encodePassword,email);

        return ResponseEntity.ok("password updated successfully");

    }


    private Integer otpGenerator()
    {
        // Generate a random 6-digit number between 100,000 and 999,999.
        Random random = new Random();

        return random.nextInt(100_000,999_999);
    }
}
