package org.example.backend.dto.taskDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Setter
@Getter
public class RequestTaskDTO {

    private String taskName;
    private double maxGrade;
    private String description;
    private MultipartFile attachment;
    private String isActive ;
    private LocalDate deadline;
    private Long courseId;


}
