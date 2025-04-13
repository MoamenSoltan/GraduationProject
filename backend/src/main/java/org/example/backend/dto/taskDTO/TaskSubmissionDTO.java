package org.example.backend.dto.taskDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class TaskSubmissionDTO {
    private MultipartFile attachment;

}
