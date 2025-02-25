package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.util.RoleType;

import java.util.List;

@Entity
@Table(name = "roles")
//@Setter
//@Getter

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleType roleName;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;



    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName=" + roleName +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleType roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
