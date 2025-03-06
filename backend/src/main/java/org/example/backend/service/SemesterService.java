package org.example.backend.service;

import org.example.backend.dto.SemesterRequestDTO;
import org.example.backend.dto.SemesterResponseDTO;
import org.example.backend.entity.Semester;
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

    public SemesterResponseDTO createSemester(SemesterRequestDTO dto)
    {
        Semester semester = SemesterMapper.RequestToEntity(dto);
        semester.setCreatedAt(LocalDateTime.now());

        Semester savedSemester = semesterRepository.save(semester);

        return SemesterMapper.EntityToResponse(savedSemester);
    }

    public List<SemesterResponseDTO> getAllSemesters() {
        List<Semester> semesters = semesterRepository.findAll();
        return semesters.stream()
                .map(SemesterMapper::EntityToResponse)
                .collect(Collectors.toList());
    }
}
