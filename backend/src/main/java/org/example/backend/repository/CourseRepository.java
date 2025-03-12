package org.example.backend.repository;

import org.example.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("select c from Course c where c.courseCode=:code")
    Optional<Course> findByCode(@Param("code") String code);
}
