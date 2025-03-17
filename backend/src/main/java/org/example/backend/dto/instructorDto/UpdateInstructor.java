package org.example.backend.dto.instructorDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdateInstructor {
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private MultipartFile personalImage;
}
