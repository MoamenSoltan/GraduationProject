package org.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.Department;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
@Setter
@Getter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructorId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @OneToOne(mappedBy = "headOfDepartment",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonBackReference
    private Department managedDepartment;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

}