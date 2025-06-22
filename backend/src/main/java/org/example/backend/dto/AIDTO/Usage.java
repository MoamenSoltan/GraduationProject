package org.example.backend.dto.AIDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;

}
