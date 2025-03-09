package org.example.backend.repository;

import org.example.backend.entity.Instructor;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {


    Optional<Student> findByUser(User user);
    @Query("select s from Student s where s.user.email=:email")
    Optional<Student> findStudentByEmail(@Param("email") String email);
}
