package org.example.backend.mapper;

import org.example.backend.dto.SemesterRequestDTO;
import org.example.backend.dto.SemesterResponseDTO;
import org.example.backend.entity.Semester;
import org.example.backend.entity.SemesterId;
import org.example.backend.enums.SemesterName;

import java.time.LocalDateTime;

public class SemesterMapper {
    // Map SemesterRequestDTO to Semester entity
    public static Semester RequestToEntity(SemesterRequestDTO requestDTO) {
        Semester semester = new Semester();

        // Set composite key (SemesterId)
        SemesterId semesterId = new SemesterId();
        semesterId.setYearLevel(requestDTO.getYearLevel());
        if (requestDTO.getSemesterName() != null) {
            semesterId.setSemesterName(SemesterName.valueOf(requestDTO.getSemesterName().name()));
        }
        semester.setSemesterId(semesterId);


        // Set other fields
        semester.setStartDate(requestDTO.getStartDate());
        semester.setEndDate(requestDTO.getEndDate());
        semester.setIsActive(requestDTO.getIsActive());
        semester.setCreatedAt(LocalDateTime.now());

        return semester;
    }

    // Map Semester entity to SemesterResponseDTO
    public static SemesterResponseDTO EntityToResponse(Semester entity) {
        SemesterResponseDTO dto = new SemesterResponseDTO();

        // Use composite key fields instead of a single semesterId
        SemesterId id = entity.getSemesterId();
        dto.setYearLevel(id.getYearLevel());
        dto.setSemesterName(id.getSemesterName());

        // Set other fields
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}