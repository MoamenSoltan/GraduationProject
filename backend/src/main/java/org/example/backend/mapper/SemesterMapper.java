package org.example.backend.mapper;

import org.example.backend.dto.SemesterRequestDTO;
import org.example.backend.dto.SemesterResponseDTO;
import org.example.backend.entity.Semester;
import org.example.backend.enums.SemesterName;

import java.time.LocalDateTime;

public class SemesterMapper {
    // Mappers for Semester entity
    public static Semester RequestToEntity(SemesterRequestDTO requestDTO)
    {
        Semester semester = new Semester();

        if (requestDTO.getSemesterName() != null) {
            semester.setSemesterName(SemesterName.valueOf(requestDTO.getSemesterName().name()));
        }
        semester.setYearLevel(requestDTO.getYearLevel());
        semester.setStartDate(requestDTO.getStartDate());
        semester.setEndDate(requestDTO.getEndDate());
        semester.setIsActive(requestDTO.getIsActive());
        semester.setCreatedAt(LocalDateTime.now());

        return semester;
    }

    public static SemesterResponseDTO EntityToResponse(Semester entity)
    {

        SemesterResponseDTO dto = new SemesterResponseDTO();
        dto.setSemesterId(entity.getSemesterId());
        dto.setYearLevel(entity.getYearLevel());
        dto.setSemesterName(entity.getSemesterName());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}
