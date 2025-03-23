package org.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.GenderType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private GenderType gender;
    // other fields and relationships


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private List<Role> roleList;

    @OneToMany(mappedBy = "user")
    private List<ForgotPassword> forgotPassword;


    public void addRole(Role role) {
        if (roleList == null) {
            roleList = new ArrayList<>();
        }
        if (!roleList.contains(role)) {  // Prevent duplicates
            roleList.add(role);
            role.getUsers().add(this);
        }
    }

}
