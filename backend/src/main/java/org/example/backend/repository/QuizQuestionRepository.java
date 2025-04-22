package org.example.backend.repository;

import org.example.backend.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    // Custom query methods can be defined here if needed

}
