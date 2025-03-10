package org.example.backend.dto.AnnouncementDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.AnnouncementType;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnnouncementResponseDTO {
    private Long announcementId;
    private String title;
    private String description;
    private AnnouncementType type;
    private Integer courseId;
    private String courseName;
    private Integer instructorId;
    private String instructorName;
    private LocalDateTime announcementDate;
    private LocalDateTime createdAt;
}
