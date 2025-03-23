package org.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailBody {
    private String to;
    private String subject;
    private String text;
}
