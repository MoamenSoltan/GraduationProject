package org.example.backend.mapper;

import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.StudentResponseDTO;
import org.example.backend.dto.SubmissionResponseDTO;
import org.example.backend.entity.Student;

public class StudentMapper {

    public static StudentResponseDTO toStudentResponseDTO(Student entity)
    {
        StudentResponseDTO dto =new StudentResponseDTO();
        dto.setStudentId(entity.getStudentId());
        dto.setAcademicYear(entity.getAcademicYear());
        dto.setGpa(entity.getGpa());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
            dto.setFirstName(entity.getUser().getFirstName());
            dto.setLastName(entity.getUser().getLastName());
            dto.setEmail(entity.getUser().getEmail());
            dto.setGender(entity.getUser().getGender());
        }

        if (entity.getDepartment() != null) {
            DepartmentDTO deptDto = new DepartmentDTO();
            deptDto.setDepartmentId(entity.getDepartment().getDepartmentId());
            deptDto.setDepartmentName(entity.getDepartment().getDepartmentName());
            dto.setDepartment(deptDto);
        }

        dto.setFeesStatus(entity.getFeesStatus());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getSubmissionRequest() != null) {
            SubmissionResponseDTO reqDto = new SubmissionResponseDTO();
            reqDto.setId(entity.getSubmissionRequest().getId());
            reqDto.setHighSchoolName(entity.getSubmissionRequest().getHighSchoolName());
            reqDto.setGraduationYear(entity.getSubmissionRequest().getGraduationYear());
            reqDto.setHighSchoolGpa(entity.getSubmissionRequest().getHighSchoolGpa());
            reqDto.setHighSchoolCertificate(entity.getSubmissionRequest().getHighSchoolCertificate());
            reqDto.setPhoneNumber(entity.getSubmissionRequest().getPhoneNumber());
            reqDto.setIdPhoto(entity.getSubmissionRequest().getIdPhoto());
            reqDto.setPersonalPhoto(entity.getSubmissionRequest().getPersonalPhoto());
            reqDto.setCountry(entity.getSubmissionRequest().getCountry());
            reqDto.setCity(entity.getSubmissionRequest().getCity());
            reqDto.setAddress(entity.getSubmissionRequest().getAddress());
            dto.setSubmissionResponseDTO(reqDto);
        }
        return dto;
    }
}
