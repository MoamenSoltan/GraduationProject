package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MaterialResponseDTO {
    private long materialId;
    private String title;
    private String description;
    private String filePath;
    private String createdAt;
    private boolean isDeleted;


}
