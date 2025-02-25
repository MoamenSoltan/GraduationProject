package org.example.backend.repository;

import org.example.backend.entity.SubmissionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionReqRepository extends JpaRepository<SubmissionRequest,Integer> {
}
