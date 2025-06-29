package org.example.backend.controller;

import org.example.backend.dto.QuizDTO.*;
import org.example.backend.service.QuizService;
import org.example.backend.service.QuizSubmissionService;
import org.example.backend.util.CurrentUser;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("instructor/quiz")
public class QuizInstructorController {
    private final QuizService quizService;
    private final QuizSubmissionService quizSubmissionService;

    public QuizInstructorController(QuizService quizService, QuizSubmissionService quizSubmissionService) {
        this.quizService = quizService;
        this.quizSubmissionService = quizSubmissionService;
    }


    @PostMapping("/course/{courseId}/create")
    public ResponseEntity<?> createQuiz(@PathVariable("courseId") Long courseId , @RequestBody QuizRequestDTO quizRequestDTO) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        String response=quizService.createQuiz(courseId, instructorEmail, quizRequestDTO);
        return ResponseEntity.ok("Quiz created successfully ");
    }
    @GetMapping("/course/{courseId}/get/{quizId}")
    public ResponseEntity<QuizResponseDTO> getQuiz(@PathVariable("courseId") Long courseId , @PathVariable("quizId") Long quizId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        QuizResponseDTO response=quizService.getQuiz(courseId, instructorEmail, quizId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/course/{courseId}/delete/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("courseId") Long courseId , @PathVariable("quizId") Long quizId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        String response=quizService.deleteQuiz(courseId, instructorEmail, quizId);
        return ResponseEntity.ok("Quiz deleted successfully ");
    }

    @GetMapping("/course/{courseId}/getAll")
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes(@PathVariable("courseId") Long courseId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        return ResponseEntity.ok(quizService.getAllQuizzes(courseId, instructorEmail));
    }
    @PutMapping("/course/{courseId}/update/{quizId}/question/{questionId}")
    public ResponseEntity<?> updateQuiz(@PathVariable("courseId") Long courseId ,
                                        @PathVariable("quizId") Long quizId ,
                                        @PathVariable("questionId") Long questionId,
                                        @RequestBody QuestionResponseDTO questionDTO) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        String response=quizService.updateQuestionsQuiz(courseId, instructorEmail, quizId, questionId, questionDTO);
        return ResponseEntity.ok("Quiz updated successfully ");
    }

    @DeleteMapping("/course/{courseId}/delete/{quizId}/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("courseId") Long courseId ,
                                            @PathVariable("quizId") Long quizId ,
                                            @PathVariable("questionId") Long questionId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        String response=quizService.deleteQuestion(courseId, instructorEmail, quizId, questionId);
        return ResponseEntity.ok("Question deleted successfully ");
    }

    @GetMapping("/{quizId}/course/{courseId}/student/{studentId}/submission")
    public ResponseEntity<QuizSubmissionInstructor> getQuizSubmission(@PathVariable("quizId") Long quizId,
                                                                      @PathVariable("courseId") Long courseId,
                                                                      @PathVariable("studentId") Long studentId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail = authentication.getName();
        return ResponseEntity.ok(quizSubmissionService.getQuizSubmission(courseId, instructorEmail, quizId, studentId));
    }

    @GetMapping("/{quizId}/course/{courseId}/student/submission")
    public ResponseEntity<List<?>> getAllStudentSubmission(
            @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId,
            @CurrentUser String instructorEmail
    )
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String instructorEmail2 = authentication.getName();
        System.out.println("Current instructor email: " + instructorEmail2);
        System.out.println("instructorEmail = " + instructorEmail);
        List<?> submissionInstructors=quizSubmissionService.getAllStudentSubmission(quizId,courseId,instructorEmail);
        return ResponseEntity.ok( submissionInstructors);
    }

    @GetMapping("/{quizId}/course/{courseId}/result/download")
    public ResponseEntity<InputStreamResource> downloadQuizResult(
            @PathVariable("quizId") Long quizId,
            @PathVariable("courseId") Long courseId,
            @CurrentUser String instructorEmail
    ) {
        ByteArrayInputStream csvData = quizSubmissionService.downloadQuizResult(quizId, courseId, instructorEmail);
        HttpHeaders headers=new HttpHeaders();

        String filename = "quiz_" + quizId + "_results.csv";
        headers.add("Content-Disposition", "attachment; filename="+filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csvData));

    }



}
