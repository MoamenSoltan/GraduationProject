package org.example.backend.repository;

import org.example.backend.entity.Course;
import org.example.backend.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("select c from Course c where c.courseCode=:code")
    Optional<Course> findByCode(@Param("code") String code);

    Optional<List<Course>> findByInstructor(Instructor instructor);

    @Modifying
    @Query("UPDATE Course c SET c.instructor = NULL WHERE c.instructor.id = :instructorId")
    void removeInstructorFromCourses(@Param("instructorId") int instructorId);

}
