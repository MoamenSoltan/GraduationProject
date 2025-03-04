package org.example.backend.dto;

import lombok.Data;
import org.example.backend.entity.Department;
import org.example.backend.enums.DepartmentName;

import java.time.LocalDateTime;

@Data
public class DepartmentDTO {
    private int departmentId;
    private DepartmentName departmentName;
    private LocalDateTime createdAt;
}
