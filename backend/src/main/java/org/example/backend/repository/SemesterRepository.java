package org.example.backend.repository;

import org.example.backend.entity.Semester;
import org.example.backend.entity.SemesterId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, SemesterId> {
}
