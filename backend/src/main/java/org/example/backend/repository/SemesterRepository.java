package org.example.backend.repository;

import org.example.backend.entity.Semester;
import org.example.backend.entity.SemesterId;
import org.example.backend.enums.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, SemesterId> {

    @Query("select s from  Semester s order by s.semesterId.yearLevel desc limit 1")
    Optional<Semester> findTopSemester();

    @Query("select s from Semester s where s.semesterId.yearLevel = ?1 and s.semesterId.semesterName = ?2")
    Optional<Semester> findBySemesterId(int yearLevel, SemesterName semesterName);
}
