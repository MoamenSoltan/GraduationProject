package org.example.backend.repository;

import org.example.backend.entity.Instructor;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor,Integer> {
    @Query("select i from Instructor i where i.user.email=:email")
    Optional<Instructor> getByEmail(@Param("email") String email);

    Optional<Instructor> findByUser(User user);

    @Modifying
    @Query(value = "DELETE FROM user_roles ur WHERE ur.user_id = :userId" ,nativeQuery = true)
    void deleteByUserId(@Param("userId") int userId);


}
