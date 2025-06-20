package org.example.backend.repository;


import org.example.backend.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("SELECT m FROM Material m WHERE m.course.courseId = :courseId")
    List<Material> findByCourseId(long courseId);
}
