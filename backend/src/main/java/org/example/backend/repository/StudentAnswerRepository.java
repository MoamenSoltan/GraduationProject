package org.example.backend.repository;

import org.example.backend.entity.QuizSubmission;
import org.example.backend.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    @Query("select sa from StudentAnswer sa where sa.submission=?1")
    List<StudentAnswer> findAllBySubmission(QuizSubmission quizSubmission);
    // Custom query methods can be defined here if needed
}
