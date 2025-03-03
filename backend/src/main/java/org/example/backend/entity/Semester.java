package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
@Entity
@Table(name = "semester")
@Setter
@Getter
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int semesterId;
    private Integer yearLevel;
    private SemesterName semesterName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isActive = false;
    private LocalDateTime createdAt;


}
