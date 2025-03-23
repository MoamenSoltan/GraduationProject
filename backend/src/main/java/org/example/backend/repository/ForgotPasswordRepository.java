package org.example.backend.repository;

import org.example.backend.entity.ForgotPassword;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    // Find OTP by user and validate expiration
    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    // Find active OTPs that haven't expired
    Optional<ForgotPassword> findByUserAndExpirationTimeAfter(User user, Date currentTime);

    // Delete expired OTPs (if necessary, can be done in the controller too)
    void deleteByUserAndExpirationTimeBefore(User user, Date expirationTime);
}
