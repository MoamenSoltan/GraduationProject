package org.example.backend.repository;

import org.example.backend.entity.TaskSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSubmissionRepository extends JpaRepository<TaskSubmission, Integer> {

}
