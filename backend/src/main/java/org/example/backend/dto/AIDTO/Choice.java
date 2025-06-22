package org.example.backend.dto.AIDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Choice {
    private Object logprobs;
    private String finish_reason;
    private String native_finish_reason;
    private int index;
    private ChatMessage  message;
}
