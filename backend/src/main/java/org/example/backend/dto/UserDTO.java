package org.example.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.User;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String password;
    private List<String> roles;


}
