package org.example.backend.dto.AIDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AnnotationFile {
    private String hash;
    private String name;
    private List<AnnotationContent> content;
}
