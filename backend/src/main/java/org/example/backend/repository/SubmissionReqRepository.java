package org.example.backend.repository;

import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmissionReqRepository extends JpaRepository<SubmissionRequest,Integer> {
    @Query("SELECT s FROM SubmissionRequest s where s.admissionStatus=:status or :status is null")
    List<SubmissionRequest> getSubmissionRequestByStatus(@Param("status") AdmissionStatus status);
}
