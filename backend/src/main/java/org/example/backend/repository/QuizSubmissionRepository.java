package org.example.backend.repository;

import org.example.backend.entity.Course;
import org.example.backend.entity.Quiz;
import org.example.backend.entity.QuizSubmission;
import org.example.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizSubmissionRepository  extends JpaRepository<QuizSubmission,Long> {
    @Query("select qs from QuizSubmission qs where qs.quiz=?1 and qs.student=?2")
    Optional<QuizSubmission> findByQuizAndStudent(Quiz quiz, Student student);

    @Query("SELECT qs FROM QuizSubmission qs WHERE qs.quiz.id = :quizId AND qs.student.studentId = :studentId")
    Optional<QuizSubmission> findByQuizIdAndStudentId(@Param("quizId") Long quizId, @Param("studentId") Long studentId);

    @Query("select qs from QuizSubmission  qs where qs.quiz.course=:course and qs.quiz.id=:quizId")
    List<QuizSubmission> findAllByCourse(@Param("course") Course course,@Param("quizId") Long quizId);
}
