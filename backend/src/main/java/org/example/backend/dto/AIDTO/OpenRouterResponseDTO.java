package org.example.backend.dto.AIDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class OpenRouterResponseDTO {
    private String id;
    private String provider;
    private String model;
    private String object;
    private long created;
    private List<Choice> choices;
    private Usage usage;
}
