package org.example.backend.dto.instructorDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructorDTO {
    private Integer instructorId;
    private String firstName;
    private String lastName;
    private String email;
}
