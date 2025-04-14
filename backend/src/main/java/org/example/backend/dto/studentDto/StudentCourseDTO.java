package org.example.backend.dto.studentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

public class StudentCourseDTO {
    private Long studentId;
    private String username;
    private String email;
    private Double degree;

    public StudentCourseDTO(Long studentId, String username, String email, Double degree) {
        this.studentId = studentId;
        this.username = username;
        this.email = email;
        this.degree = degree;
    }

}
