package org.example.backend.repository;

import org.example.backend.entity.TaskSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskSubmissionRepository extends JpaRepository<TaskSubmission, Integer> {

    @Query("select ts from TaskSubmission ts where ts.student.user.email=:email")
    List<TaskSubmission> getALLTaskSubmittedByStudent(@Param("email") String email);

    @Query("select ts from TaskSubmission ts where ts.student.user.email=:email and ts.task.course.courseId=:courseId")
    List<TaskSubmission> getALLTaskSubmittedByStudent(@Param("email") String email,@Param("courseId") Long courseId);

    @Query("select ts from TaskSubmission ts where ts.task.course.instructor.user.email = :email and ts.task.course.courseId = :courseId and ts.task.id = :taskId")
    List<TaskSubmission> findSubmissionsByInstructorCourseAndTask(String email, int courseId, int taskId);
}
