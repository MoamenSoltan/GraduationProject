package org.example.backend.dto.taskDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateGrade {
    private int submissionId;
    private double grade;
}
