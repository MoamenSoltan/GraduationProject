package org.example.backend.repository;

import org.example.backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Query("select f from RefreshToken f where f.token=:token")
    Optional<RefreshToken> getRefreshToken(@Param("token") String token);

    Optional<RefreshToken> findByToken(String refresh);
}
