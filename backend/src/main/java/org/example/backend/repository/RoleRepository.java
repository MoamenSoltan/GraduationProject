package org.example.backend.repository;

import org.example.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query("select r from Role r where r.id=3")
    Role getStudentRole();

    @Query("select r from Role r where r.id=2")
    Optional<Role> getInstructorRole();


}
