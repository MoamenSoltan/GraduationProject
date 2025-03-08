package org.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

import java.io.Serializable;
@Embeddable
@Setter
@Getter
public class SemesterId implements Serializable {
    @Column(name = "year_level")
    private Integer yearLevel;

    @Column(name = "semester_name")
    @Enumerated(EnumType.STRING)
    private SemesterName semesterName;


}
