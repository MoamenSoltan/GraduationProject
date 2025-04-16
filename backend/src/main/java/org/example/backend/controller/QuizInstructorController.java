package org.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("instructor/quiz")
public class QuizInstructorController {

    @PostMapping("/course/{courseId}/create")
    public ResponseEntity<?> createQuiz(@PathVariable("courseId") Long courseId) {

        return ResponseEntity.ok("Quiz created successfully for course ID: " + courseId);
    }
}
