package org.example.backend.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
