package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "student_answers")
@Entity
@Getter
@Setter
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;
    @Column(name = "selected_option")
    private String optionAnswer;
    @Column(columnDefinition = "TEXT")
    private String shortAnswer;
    private boolean isCorrect;
    @Column(name = "score_awarded")
    private int score;


    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private QuizSubmission submission;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion question;
}
