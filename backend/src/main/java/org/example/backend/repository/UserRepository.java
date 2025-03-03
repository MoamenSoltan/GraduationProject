package org.example.backend.repository;


import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUsersByEmail (String email);

    @Query("select u from User u where u.email=:email")
    Optional<User> getUserByEmail (@Param("email") String email);
}
