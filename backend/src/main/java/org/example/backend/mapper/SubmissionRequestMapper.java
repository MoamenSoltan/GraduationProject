package org.example.backend.mapper;

import org.example.backend.dto.SubmissionInfoRequestDTO;
import org.example.backend.dto.SubmissionRequestDto;
import org.example.backend.entity.Student;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.enums.FeesStatus;
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
        request.setGender(dto.getGender());
        return request;
    }

    public static Student mapToStudent(SubmissionRequest request)
    {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        user.setGender(request.getGender());


        Student student = new Student();

        student.setSubmissionRequest(request);
        student.setUser(user);
//        student.setSubmissionRequest();
        student.setAcademicYear(1);
        student.setFeesStatus(FeesStatus.pending);
        student.setGpa(0.0);


        return student;
    }

    public static User mapToUser(SubmissionRequest  request)
    {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        user.setGender(request.getGender());

        return user;
    }

    public static SubmissionRequest toEntity(SubmissionInfoRequestDTO dto)
    {
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
        request.setGender(dto.getGender());

        return request;
    }
}
