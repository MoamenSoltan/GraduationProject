package org.example.backend.service;

import org.example.backend.dto.SemesterRequestDTO;
import org.example.backend.dto.SemesterResponseDTO;
import org.example.backend.entity.Semester;
import org.example.backend.entity.SemesterId;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.SemesterMapper;
import org.example.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public SemesterResponseDTO createSemester(SemesterRequestDTO dto) {
        if (dto.getYearLevel() == null || dto.getSemesterName() == null) {
            throw new IllegalArgumentException("Year level and semester name are required");
        }

        // Check for duplicate semester (optional but recommended)
        SemesterId semesterId = new SemesterId();
        semesterId.setYearLevel(dto.getYearLevel());
        semesterId.setSemesterName(dto.getSemesterName());
        if (semesterRepository.existsById(semesterId)) {
            throw new IllegalStateException("Semester with yearLevel " + dto.getYearLevel() + " and semesterName " + dto.getSemesterName() + " already exists");
        }

        Semester semester = SemesterMapper.RequestToEntity(dto);

        Semester savedSemester = semesterRepository.save(semester);
        return SemesterMapper.EntityToResponse(savedSemester);
    }

    public List<SemesterResponseDTO> getAllSemesters() {
        List<Semester> semesters = semesterRepository.findAll();
        return semesters.stream()
                .map(SemesterMapper::EntityToResponse)
                .collect(Collectors.toList());
    }

    // Optional: Add method to get a specific semester by composite key
    public SemesterResponseDTO getSemesterById(Integer yearLevel, String semesterName) {
        SemesterId semesterId = new SemesterId();
        semesterId.setYearLevel(yearLevel);
        semesterId.setSemesterName(Enum.valueOf(org.example.backend.enums.SemesterName.class, semesterName));

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new ResourceNotFound("Semester ", "yearLevel" ,yearLevel));
        return SemesterMapper.EntityToResponse(semester);
    }
}