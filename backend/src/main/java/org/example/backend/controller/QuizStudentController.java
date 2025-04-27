package org.example.backend.controller;

import org.example.backend.dto.QuizDTO.*;
import org.example.backend.exception.QuizAlreadySubmittedException;
import org.example.backend.service.QuizSubmissionService;
import org.example.backend.util.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student/quiz")
public class QuizStudentController {

    private final QuizSubmissionService quizSubmissionService;

    public QuizStudentController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @PostMapping("/{quizId}/course/{courseId}/submit")
    public ResponseEntity<?> submitQuiz(
            @RequestBody List<AnswerDTO> answers
            , @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();

        quizSubmissionService.submitQuiz(answers, quizId, courseId, studentEmail);

        try {
            quizSubmissionService.submitQuiz(answers, quizId, courseId, studentEmail);
            return ResponseEntity.ok(Map.of(
                    "message", "Quiz submitted successfully",
                    "status", "unsubmitted"
            ));
        } catch (QuizAlreadySubmittedException e) {
            return ResponseEntity.ok(Map.of(
                    "message", "Quiz already submitted",
                    "status", "submitted"
            ));
        }

    }

   // @PostMapping("/{quizId}/course/{courseId}/submit")
    public ResponseEntity<String> submitQuizz(
            @RequestBody List<AnswerDTO> answers
            , @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String studentEmail = authentication.getName();

        quizSubmissionService.submitQuiz(answers, quizId, courseId, studentEmail);
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
