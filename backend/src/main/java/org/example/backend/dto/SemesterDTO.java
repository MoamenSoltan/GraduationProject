package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SemesterDTO {
    private Integer semesterId;
    private Integer yearLevel;
    private String semesterName;
}
