package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.QuestionType;

import java.util.List;

@Entity
@Table(name = "quiz_questions")
@Setter
@Getter
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;
    private String questionText;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType =QuestionType.MCQ;


    private String answer;

    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOptionEntity> options;

}
