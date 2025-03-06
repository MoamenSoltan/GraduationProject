package org.example.backend.repository;

import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {


    Optional<Student> findByUser(User user);
}
