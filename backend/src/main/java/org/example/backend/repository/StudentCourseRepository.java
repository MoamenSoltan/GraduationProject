package org.example.backend.repository;

import org.example.backend.entity.StudentCourse;
import org.example.backend.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
}
