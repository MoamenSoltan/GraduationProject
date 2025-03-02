package org.example.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        ProblemDetail problemDetail = null;

        if (ex instanceof BadCredentialsException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
            problemDetail.setProperty("error", "Authentication failed");
        }

        if (ex instanceof AccessDeniedException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access to the resource is prohibited.");
            problemDetail.setProperty("error", "not authorized");
        }

        if (ex instanceof SignatureException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
            problemDetail.setProperty("error", "Invalid JWT signature");
        }

        if (ex instanceof ExpiredJwtException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "JWT token expired");
            problemDetail.setProperty("error", "JWT token expired");
        }

        if (ex instanceof DataIntegrityViolationException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Duplicate entry");
            problemDetail.setProperty("error", "Email already exists");
        }

        if (ex instanceof ResourceNotFound) {
            ResourceNotFound rnfe = (ResourceNotFound) ex;
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, rnfe.getMessage());
            problemDetail.setProperty("resource", rnfe.getResourceName());
            problemDetail.setProperty("field", rnfe.getFieldName());
            problemDetail.setProperty("value", rnfe.getFieldValue());
        }

        if (ex instanceof MethodArgumentNotValidException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation error");
            Map<String, String> errors = new HashMap<>();
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            problemDetail.setProperty("validation_errors", errors);
        }

        if (problemDetail == null) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            problemDetail.setProperty("error", ex.getMessage());
        }

        return problemDetail;
    }
}
