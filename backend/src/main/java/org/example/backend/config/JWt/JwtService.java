package org.example.backend.config.JWt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.backend.entity.RefreshToken;
import org.example.backend.entity.User;
import org.example.backend.repository.RefreshTokenRepository;
import org.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    @Value("${application.security.jwt.secret-key}")
    private  String secretKey ;
    @Value("${application.security.jwt.access-token-expiration}")
    private  long VALIDITY ;// 30*60*1000  => 30 minutes
    @Value("${application.security.jwt.refresh-token-expiration}")
    private  long REFRESH_VALIDITY ;// 7*24*60*60*1000 => 7 days

    public JwtService(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    private SecretKey getKey() {
        byte[] key = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateAccessToken(UserDetails userDetails) {
       return generateToken(userDetails,VALIDITY,"ACCESS");
    }
    public String generateRefreshToken(UserDetails userDetails)
    {
       String refreshToken= generateToken(userDetails,REFRESH_VALIDITY,"REFRESH");
       User user =userService.getEmail(userDetails.getUsername());
       saveRefreshToken(refreshToken,user);
       return refreshToken;
    }

    private String generateToken(UserDetails userDetails,long expirationTime,String tokenType)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        claims.put("token_type", tokenType);
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey())
                .compact();
    }


    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims= Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public boolean isValidateToken(String token) {
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public boolean isRefreshTokenValid(String token, User user) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.getRefreshToken(token);
        if(refreshToken.isEmpty()) return false;

        RefreshToken token1=refreshToken.get();
        Claims claims=getClaims(token);
        String tokenType = claims.get("token_type", String.class);

        return (token1.getUser() != null && // Add null check
                token1.getUser().getId()==(user.getId()) &&
                !token1.isRevoked() &&
                getClaims(token).getExpiration().after(new Date(System.currentTimeMillis())) &&
                "REFRESH".equals(tokenType));
    }

    public void saveRefreshToken(String token ,User user)
    {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_VALIDITY));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);
    }
}
