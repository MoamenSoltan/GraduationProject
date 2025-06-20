package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "materials")
@Setter
@Getter
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long materialId;
    private String title;
    private String description;
    @Column(name = "file_url")
    private String filePath;
    @Column(name = "upload_date")
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
