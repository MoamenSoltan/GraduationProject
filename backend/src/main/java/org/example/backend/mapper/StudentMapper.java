package org.example.backend.mapper;

import org.example.backend.dto.*;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.studentDto.StudentProfile;
import org.example.backend.dto.studentDto.StudentResponseDTO;
import org.example.backend.dto.submissionDto.SubmissionResponseDTO;
import org.example.backend.entity.Student;
import org.example.backend.entity.StudentCourse;
import org.example.backend.entity.User;
import org.example.backend.util.FileResponse;

import java.util.ArrayList;
import java.util.List;

public class StudentMapper {

    public static StudentResponseDTO toStudentResponseDTO(Student entity)
    {
        FileResponse response=new FileResponse();
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
            reqDto.setHighSchoolCertificate(response.getFileName(entity.getSubmissionRequest().getHighSchoolCertificate()));
            reqDto.setPhoneNumber(entity.getSubmissionRequest().getPhoneNumber());
            reqDto.setIdPhoto(response.getFileName(entity.getSubmissionRequest().getIdPhoto()));
            reqDto.setPersonalPhoto(response.getFileName(entity.getSubmissionRequest().getPersonalPhoto()));
            reqDto.setCountry(entity.getSubmissionRequest().getCountry());
            reqDto.setCity(entity.getSubmissionRequest().getCity());
            reqDto.setAddress(entity.getSubmissionRequest().getAddress());
            dto.setSubmissionResponseDTO(reqDto);
        }

        if(entity.getUser().getRoleList()!=null){
            List<String> roles = new ArrayList<>();
            for(var role: entity.getUser().getRoleList()){
                roles.add(String.valueOf(role.getRoleName()));
            }
            dto.setRoles(roles);
        }

        if(entity.getStudentCourse()!=null)
        {

            List<CourseDTO> courseDTOS =new ArrayList<>();
            for (var course:entity.getStudentCourse())
            {
//                System.out.println("course : "+course.getCourse().getCourseCode());
                CourseDTO courseDTO=new CourseDTO();
                courseDTO.setCourseCode(course.getCourse().getCourseCode());
                courseDTO.setCourseName(course.getCourse().getCourseName());
                courseDTO.setCourseId(course.getCourse().getCourseId());
                courseDTO.setGrade(course.getCourse().getYear());

                courseDTOS.add(courseDTO);
            }
            dto.setCourses(courseDTOS);

        }


        return dto;
    }

    public static StudentProfile toStudentProfile(Student student)
    {
        StudentProfile profile = new StudentProfile();
        profile.setId(student.getStudentId());
        profile.setGrade(student.getAcademicYear());
        profile.setGpa(student.getGpa());
        profile.setAddress(student.getSubmissionRequest().getAddress());
        profile.setCity(student.getSubmissionRequest().getCity());
        profile.setCountry(student.getSubmissionRequest().getCountry());



        if (student.getUser() != null) {
//            profile.setUserId(student.getUser().getId());
            profile.setUsername(student.getUser().getFirstName()+" " + student.getUser().getLastName());
            profile.setEmail(student.getUser().getEmail());
            profile.setLastName(student.getUser().getLastName());
            profile.setFirstName(student.getUser().getFirstName());


        }

        if (student.getDepartment() != null) {
            profile.setDepartment(student.getDepartment().getDepartmentName());
        }

        profile.setProfileImage(new FileResponse().getFileName(student.getSubmissionRequest().getPersonalPhoto()));

        if(student.getStudentCourse()!=null &&!student.getStudentCourse().isEmpty())
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
        else {
            profile.setCourses(new ArrayList<>());
            profile.setTotalCredit(0);
        }

        return profile;
    }

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(String.valueOf(user.getGender()));

        List<String> roles = new ArrayList<>();
        for(var role: user.getRoleList()){
            roles.add(String.valueOf(role.getRoleName()));
        }

        userDTO.setRoles(roles);
        return userDTO;
    }


}
