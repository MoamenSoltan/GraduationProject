package org.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.Instructor;
import org.example.backend.enums.DepartmentName;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "department")
@Setter
@Getter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Enumerated(EnumType.STRING)
    private DepartmentName departmentName = DepartmentName.general;

    private LocalDateTime createdAt;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "head_of_department_id")
    @JsonIgnore
    @JsonManagedReference
    private Instructor headOfDepartment;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Instructor> instructors;

//    @JsonIgnore // Prevent serialization loop via setter
//    public void setHeadOfDepartment(Instructor instructor) {
//        if (this.headOfDepartment == instructor) {
//            return;
//        }
//        if (this.headOfDepartment != null) {
//            this.headOfDepartment.setManagedDepartment(null);
//        }
//        this.headOfDepartment = instructor;
//        if (instructor != null && instructor.getManagedDepartment() != this) {
//            instructor.setManagedDepartment(this);
//        }
//    }
}