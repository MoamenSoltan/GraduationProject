package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class MaterialRequestDTO {

    private String title;
    private String description;
    private MultipartFile filePath;
    private long courseId;


}
