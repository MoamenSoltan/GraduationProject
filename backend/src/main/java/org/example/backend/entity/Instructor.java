package org.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.Department;

@Entity
@Table(name = "instructors")
@Setter
@Getter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructorId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @OneToOne(mappedBy = "headOfDepartment",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonBackReference
    private Department managedDepartment;

//    @JsonIgnore // Prevent serialization loop via setter
//    public void setManagedDepartment(Department department) {
//        if (this.managedDepartment == department) {
//            return;
//        }
//        if (this.managedDepartment != null) {
//            this.managedDepartment.setHeadOfDepartment(null);
//        }
//        this.managedDepartment = department;
//        if (department != null && department.getHeadOfDepartment() != this) {
//            department.setHeadOfDepartment(this);
//        }
//    }
}