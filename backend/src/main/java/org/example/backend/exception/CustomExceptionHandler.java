package org.example.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex)
    {
        ProblemDetail problemDetail=null;
        if(ex instanceof BadCredentialsException)
        {
            problemDetail= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            problemDetail.setProperty("access_denied","Authentication failed");


        }

        if(ex instanceof AccessDeniedException)
        {
            problemDetail= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            problemDetail.setProperty("access_denied","not authorized");

        }

        if(ex instanceof SignatureException)
        {
            problemDetail= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            problemDetail.setProperty("access_denied","JWT Signature not valid");

        }

        if(ex instanceof ExpiredJwtException)
        {
            problemDetail= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            problemDetail.setProperty("access_denied","JWT token already expired");

        }

//       else {
//            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//            problemDetail.setProperty("error", ex.getMessage());
//        }

        return problemDetail;
    }
}
