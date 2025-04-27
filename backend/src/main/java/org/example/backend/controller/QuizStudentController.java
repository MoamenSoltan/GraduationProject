package org.example.backend.controller;

import org.example.backend.dto.QuizDTO.QuizDTO;
import org.example.backend.dto.QuizDTO.QuizResponseDTO;
import org.example.backend.dto.QuizDTO.QuizResult;
import org.example.backend.dto.QuizDTO.QuizSubmissionRequest;
import org.example.backend.service.QuizSubmissionService;
import org.example.backend.util.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student/quiz")
public class QuizStudentController {

    private final QuizSubmissionService quizSubmissionService;

    public QuizStudentController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @PostMapping("/{quizId}/course/{courseId}/submit")
    public ResponseEntity<String> submitQuiz(
            @RequestBody QuizSubmissionRequest submissionRequest
            , @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();

        quizSubmissionService.submitQuiz(submissionRequest, quizId, courseId, studentEmail);
        return ResponseEntity.ok("Quiz submitted successfully");
    }
    @GetMapping("/{quizId}/course/{courseId}/get")
    public ResponseEntity<QuizResponseDTO> getQuizForStudent(
            @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();

        return ResponseEntity.ok(quizSubmissionService.getQuizForStudent(quizId, courseId, studentEmail));

    }

    @GetMapping("/course/{courseId}/getAll")
    public ResponseEntity<List<QuizDTO>> getAllQuizForStudent(

            @PathVariable("courseId") Long courseId)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();

        return ResponseEntity.ok(quizSubmissionService.getAllQuizForStudent( courseId, studentEmail));

    }
    @GetMapping("{quizId}/course/{courseId}/result")
    ResponseEntity<QuizResult> getQuizResult(
            @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();
        System.out.println("studentEmail = " + studentEmail);

        return ResponseEntity.ok(quizSubmissionService.getQuizResult(quizId, courseId, studentEmail));

    }

    @GetMapping("/{quizId}/course/{courseId}/submission")
    public ResponseEntity<?> viewSubmittedAnswer(@CurrentUser String email)
    {
        return ResponseEntity.ok(email);
    }
}
