package org.example.backend.service;

import org.example.backend.dto.AnnouncementDto.AnnouncementRequestDTO;
import org.example.backend.dto.AnnouncementDto.AnnouncementResponseDTO;
import org.example.backend.entity.Announcement;
import org.example.backend.entity.Course;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Student;
import org.example.backend.enums.AnnouncementType;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.AnnouncementMapper;
import org.example.backend.repository.AnnouncementRepository;
import org.example.backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CourseRepository courseRepository;


    public AnnouncementService(AnnouncementRepository announcementRepository,
                               CourseRepository courseRepository) {
        this.announcementRepository = announcementRepository;
        this.courseRepository = courseRepository;
    }

    public AnnouncementResponseDTO createAnnouncement(AnnouncementRequestDTO dto,Instructor instructor) {
        Course course = courseRepository.findById(Long.valueOf(dto.getCourseId()))
                .orElseThrow(() -> new ResourceNotFound("Course", "id", dto.getCourseId()));


        Announcement announcement = AnnouncementMapper.toEntity(dto);
        announcement.setCourse(course);
        announcement.setInstructor(instructor);
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return AnnouncementMapper.toResponseDTO(savedAnnouncement);
    }

    public List<AnnouncementResponseDTO> getAnnouncementsByType(AnnouncementType type) {
        List<Announcement> announcements = announcementRepository.getAllAnnouncements(type);
        return announcements.stream()
                .map(AnnouncementMapper::toResponseDTO)
                .collect(Collectors.toList());

    }

    public List<AnnouncementResponseDTO> getAnnouncements(Instructor instructor) {
        List<Announcement> announcements = announcementRepository.findByInstructor(instructor);
        return announcements.stream()
                .map(AnnouncementMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AnnouncementResponseDTO> getAnnouncementsForStudent(Student student) {
        List<Announcement> announcements = announcementRepository.findAnnouncementsForStudent(student);
        return announcements.stream()
                .map(AnnouncementMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AnnouncementResponseDTO> getAnnouncementsForStudentByType(Student student, AnnouncementType type) {
        List<Announcement> announcements = announcementRepository.findAnnouncementsForStudentByType(student, type);
        return announcements.stream()
                .map(AnnouncementMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
