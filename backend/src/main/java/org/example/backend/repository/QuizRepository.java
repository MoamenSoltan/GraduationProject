package org.example.backend.repository;

import org.example.backend.entity.Course;
import org.example.backend.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    @Query("select q from Quiz q where q.course=?1 and q.id=?2")
    Optional<Quiz> findQuizByCourseAndQuizId(Course course, Long quizId);

    @Query("select q from Quiz q where q.course.courseId=?1 and q.id=?2 ")
    Optional<Quiz> findQuizByCourseAndQuizId(Long courseId, Long quizId);

    @Query("select q from Quiz q where q.course=?1")
    List<Quiz> findAllByCourse(Course course);

    @Query("select q from Quiz q where q.course.courseId=?1")
    List<Quiz> findAllQuizzesByCourse(Long courseId);

    @Query("select q from Quiz  q where q.course.courseId=?1 and q.id=?2 and ?3 between q.startDate and q.endDate")
    Optional<Quiz> getQizIsAvailableById(Long courseId, Long quizId , LocalDateTime currentTime) ;
}
