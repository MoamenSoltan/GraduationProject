package org.example.backend.dto.QuizDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OptionResponseDTO {
    @JsonProperty("optionId")
    private Long optionId;
    @JsonProperty("option")
    private String optionText;

}
