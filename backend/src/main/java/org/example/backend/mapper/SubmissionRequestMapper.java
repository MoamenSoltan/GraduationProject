package org.example.backend.mapper;

import org.example.backend.dto.SubmissionRequestDto;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.RoleType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubmissionRequestMapper {
    public SubmissionRequest mapToEntity(SubmissionRequestDto dto) {
        SubmissionRequest request = new SubmissionRequest();
        request.setFirstName(dto.getFirstName());
        request.setLastName(dto.getLastName());
        request.setEmail(dto.getEmail());
        request.setPassword(dto.getPassword());
        request.setAcademicYear(1);
        request.setHighSchoolName(dto.getHighSchoolName());
        request.setGraduationYear(dto.getGraduationYear());
        request.setCity(dto.getCity());
        request.setCountry(dto.getCountry());
        request.setAddress(dto.getAddress());
        request.setHighSchoolGpa(dto.getHighSchoolGpa());
        request.setPhoneNumber(dto.getPhoneNumber());
        request.setUserType(RoleType.STUDENT);
        request.setAdmissionStatus(AdmissionStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        return request;
    }
}
