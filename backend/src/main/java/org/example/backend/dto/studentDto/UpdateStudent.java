package org.example.backend.dto.studentDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdateStudent {
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String address;
    private MultipartFile personalImage;
}
