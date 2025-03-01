package org.example.backend.exception;


import org.example.backend.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFound resourceNotFound)
    {
        String message = resourceNotFound.getMessage();

        ApiResponse response = new ApiResponse(false, message);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
