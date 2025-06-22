package org.example.backend.dto.AIDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class ChatMessage {
    private String role;
    private String content;
    private Object refusal;
    private Object reasoning;
    private List<Annotation> annotations;
}
