package org.example.backend.dto;

import lombok.Data;
import org.example.backend.entity.Role;

@Data
public class RoleDTO {
    private int id;
    private String roleName;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.roleName = role.getRoleName().name();
    }
}