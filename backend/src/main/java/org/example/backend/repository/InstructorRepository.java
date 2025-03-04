package org.example.backend.repository;

import org.example.backend.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor,Integer> {
    @Query("select i from Instructor i where i.user.email=:email")
    Instructor getByEmail(@Param("email") String email);
}
