package org.example.backend.repository;

import org.example.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Query("select t from Task t where t.course.courseId =:courseId")
    List<Task> findTaskByCourseId(@Param("courseId") int courseId);
}
