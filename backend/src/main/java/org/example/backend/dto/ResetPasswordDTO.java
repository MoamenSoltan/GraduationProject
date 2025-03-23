package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDTO {
    private String password;
    private String confirmPassword;
}
