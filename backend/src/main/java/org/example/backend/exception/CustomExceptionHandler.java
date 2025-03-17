package org.example.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException ex) {
        return createProblemDetail(HttpStatus.UNAUTHORIZED, "Authentication failed", "Invalid email or password.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        return createProblemDetail(HttpStatus.FORBIDDEN, "Not authorized", "Access to the resource is prohibited.");
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignatureException(SignatureException ex) {
        return createProblemDetail(HttpStatus.FORBIDDEN, "Invalid JWT signature", "Your token is invalid. Please log in again.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ProblemDetail handleMalformedJwtException(MalformedJwtException ex) {
        return createProblemDetail(HttpStatus.FORBIDDEN, "Invalid JWT token", "Your token format is incorrect. Please log in again.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException ex) {
        return createProblemDetail(HttpStatus.FORBIDDEN, "JWT token expired", "Your session has expired. Please log in again.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.BAD_REQUEST, "Validation error", "Validation failed for request.");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        problemDetail.setProperty("validation_errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(JpaSystemException.class)
    public ProblemDetail handleJpaException(JpaSystemException ex) {
        return handleEnrollmentException(ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return handleEnrollmentException(ex.getMostSpecificCause().getMessage());
    }

    /**
     * Handles enrollment-related errors (e.g., prerequisites, course limits).
     */
    private ProblemDetail handleEnrollmentException(String errorMessage) {
        if (errorMessage.contains("Student has not passed the prerequisite course")) {
            return createProblemDetail(
                    HttpStatus.BAD_REQUEST,
                    "Enrollment Error",
                    "You must pass the prerequisite course before enrolling."
            );
        }
        if (errorMessage.contains("Student cannot enroll in more than the allowed courses for this semester")) {
            return createProblemDetail(
                    HttpStatus.BAD_REQUEST,
                    "Enrollment Limit Exceeded",
                    "Student cannot enroll in more than the allowed courses for this semester."
            );
        }
        return createProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Database Error",
                "An error occurred while processing your request."
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage());
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setProperty("error", title);
        return problemDetail;
    }
}
