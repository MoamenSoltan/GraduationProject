package org.example.backend.entity;

import jakarta.persistence.*;
import org.example.backend.enums.DepartmentName;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Enumerated(EnumType.STRING)
    private DepartmentName departmentName=DepartmentName.general;
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "head_of_department_id")
    private User user;
    @OneToMany(mappedBy = "department")
    private List<Instructor> instructors;
    @OneToMany(mappedBy = "department")
    private List<Student> students;

}
