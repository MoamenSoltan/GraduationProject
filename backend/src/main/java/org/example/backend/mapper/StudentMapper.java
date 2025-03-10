package org.example.backend.mapper;

import org.example.backend.dto.*;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.studentDto.StudentProfile;
import org.example.backend.dto.studentDto.StudentResponseDTO;
import org.example.backend.dto.submissionDto.SubmissionResponseDTO;
import org.example.backend.entity.Student;
import org.example.backend.entity.StudentCourse;

import java.util.ArrayList;
import java.util.List;

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

    public static StudentProfile toStudentProfile(Student student)
    {
        StudentProfile profile = new StudentProfile();
        profile.setId(student.getStudentId());
        profile.setGrade(student.getAcademicYear());
        profile.setGpa(student.getGpa());


        if (student.getUser() != null) {
//            profile.setUserId(student.getUser().getId());
            profile.setUsername(student.getUser().getFirstName()+" " + student.getUser().getLastName());
            profile.setEmail(student.getUser().getEmail());


        }

        if (student.getDepartment() != null) {
            profile.setDepartment(student.getDepartment().getDepartmentName());
        }

        if(student.getSubmissionRequest()!=null)
        {
            profile.setProfileImage(student.getSubmissionRequest().getPersonalPhoto());
        }

        if(student.getStudentCourse()!=null)
        {
            List<CourseDTO>  courseDTOS = new ArrayList<>();
            int hours=0;
            for(StudentCourse sc: student.getStudentCourse())
            {
                CourseDTO courseDTO = new CourseDTO();

                courseDTO.setCourseId(sc.getCourse().getCourseId());
                courseDTO.setCourseName(sc.getCourse().getCourseName());
                courseDTO.setCourseCode(sc.getCourse().getCourseCode());
                courseDTO.setGrade(sc.getCourse().getYear());

                hours+=sc.getCourse().getCredit();

                courseDTOS.add(courseDTO);
            }
            profile.setCourses(courseDTOS);
            profile.setTotalCredit(hours);

        }

        return profile;
    }
}
