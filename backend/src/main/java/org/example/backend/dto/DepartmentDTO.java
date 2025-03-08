package org.example.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.Department;
import org.example.backend.enums.DepartmentName;

import java.time.LocalDateTime;

@Setter
@Getter
public class DepartmentDTO {
    private int departmentId;
    private DepartmentName departmentName;
    private LocalDateTime createdAt;
}
