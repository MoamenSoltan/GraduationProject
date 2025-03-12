package org.example.backend.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String message;
    @JsonIgnore
    private Object user;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private String personalImage;
    private String refreshToken;


}
