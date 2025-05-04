package org.example.backend.repository;

import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmissionReqRepository extends JpaRepository<SubmissionRequest,Integer> {
    @Query("SELECT s FROM SubmissionRequest s where s.admissionStatus=:status or :status is null")
    List<SubmissionRequest> getSubmissionRequestByStatus(@Param("status") AdmissionStatus status);

    @Query("select s from SubmissionRequest s where s.email = :email")
    Optional<SubmissionRequest> getByEmail(@Param("email") String email);
}
