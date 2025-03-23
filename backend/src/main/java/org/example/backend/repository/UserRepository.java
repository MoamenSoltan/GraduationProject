package org.example.backend.repository;


import jakarta.transaction.Transactional;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUsersByEmail (String email);

    @Query("select u from User u where u.email=:email")
    Optional<User> getUserByEmail (@Param("email") String email);

    @Query("update User u set u.password=?1 where u.email=?2")
    @Modifying
    @Transactional
    void updatePassword(String password,String email);
}
