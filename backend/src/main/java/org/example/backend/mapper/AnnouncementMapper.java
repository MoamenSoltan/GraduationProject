package org.example.backend.mapper;

import org.example.backend.dto.AnnouncementDto.AnnouncementRequestDTO;
import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.entity.Announcement;

public class AnnouncementMapper {

    public static Announcement toEntity(AnnouncementRequestDTO requestDTO)
    {
        Announcement announcement = new Announcement();
        announcement.setTitle(requestDTO.getTitle());
        announcement.setDescription(requestDTO.getDescription());
        announcement.setType(requestDTO.getType());
        announcement.setAnnouncementDate(requestDTO.getAnnouncementDate());
        return announcement;
    }

    public static AnnouncementResponseDTO toResponseDTO(Announcement announcement) {
        AnnouncementResponseDTO dto = new AnnouncementResponseDTO();
        dto.setAnnouncementId(announcement.getAnnouncementId());
        dto.setTitle(announcement.getTitle());
        dto.setDescription(announcement.getDescription());
        dto.setType(announcement.getType());
        dto.setCourseId(Math.toIntExact(announcement.getCourse().getCourseId()));
        dto.setCourseName(announcement.getCourse().getCourseName());
        if (announcement.getInstructor() != null) {
            dto.setInstructorId(announcement.getInstructor().getInstructorId());
            dto.setInstructorName(announcement.getInstructor().getUser().getFirstName()+" "+announcement.getInstructor().getUser().getLastName()); // Assuming Instructor has getName()
        }
        dto.setAnnouncementDate(announcement.getAnnouncementDate());
        dto.setCreatedAt(announcement.getCreatedAt());
        return dto;
    }
}
