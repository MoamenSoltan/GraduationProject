package org.example.backend.dto.AnnouncementDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.AnnouncementType;

import java.time.LocalDateTime;
@Setter
@Getter
public class AnnouncementRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Type is required")
    private AnnouncementType type;

    @NotNull(message = "Course ID is required")
    private Integer courseId;

    @NotNull(message = "Announcement date is required")
    private LocalDateTime announcementDate;
}
