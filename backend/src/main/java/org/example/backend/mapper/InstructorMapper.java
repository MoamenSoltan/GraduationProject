package org.example.backend.mapper;

import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.instructorDto.InstructorProfile;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.semesterDto.SemesterDTO;
import org.example.backend.entity.Course;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Role;
import org.example.backend.entity.User;
import org.example.backend.util.FileResponse;

import java.util.ArrayList;
import java.util.List;


public class InstructorMapper {

    public static Instructor requestToEntity(InstructorRequestDTO requestDTO)
    {
        Instructor instructor= new Instructor();

        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setGender(requestDTO.getGender());
        instructor.setPersonalImage("D:\\GraduationProject\\backend\\uploads\\profile-default-iconpng.png");
        instructor.setBio(null);
        instructor.setUser(user);

        return instructor;
    }

    public static InstructorResponseDTO  entityToResponseDTO(Instructor entity)
    {
        InstructorResponseDTO responseDTO = new InstructorResponseDTO();
        responseDTO.setPersonalImage(new FileResponse().getFileName(entity.getPersonalImage()));

        responseDTO.setInstructorId(entity.getInstructorId());
        if (entity.getUser() != null) {
            responseDTO.setFirstName(entity.getUser().getFirstName());
            responseDTO.setLastName(entity.getUser().getLastName());
            responseDTO.setEmail(entity.getUser().getEmail());
            responseDTO.setGender(entity.getUser().getGender());
        }

        if (entity.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDepartmentId(entity.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(entity.getDepartment().getDepartmentName());
            responseDTO.setDepartment(departmentDTO);
        }

        if(entity.getManagedDepartment() != null)
        {
            DepartmentDTO managedDepartmentDTO = new DepartmentDTO();
            managedDepartmentDTO.setDepartmentId(entity.getManagedDepartment().getDepartmentId());
            managedDepartmentDTO.setDepartmentName(entity.getManagedDepartment().getDepartmentName());
            responseDTO.setManagedDepartment(managedDepartmentDTO);
        }

        if (entity.getCourses()!=null&& !entity.getCourses().isEmpty())
        {
            List<CourseDTO> courses = new ArrayList<>();
            for (Course course : entity.getCourses()) {
                CourseDTO dto=new CourseDTO();
                dto.setGrade(course.getYear());
                dto.setCourseId(course.getCourseId());
                dto.setCourseName(course.getCourseName());
                dto.setCourseCode(course.getCourseCode());

                SemesterDTO semesterDTO=new SemesterDTO();
                semesterDTO.setSemesterName(course.getSemester().getSemesterId().getSemesterName().toString());
                semesterDTO.setYear(course.getSemester().getSemesterId().getYearLevel());
                semesterDTO.setYearLevel(course.getSemester().getSemesterId().getYearLevel());
                dto.setSemester(semesterDTO);
                courses.add(dto);
            }
            responseDTO.setCourses(courses);
        }

        return responseDTO;
    }

    public static InstructorProfile toInstructorProfile(Instructor entity)
    {
        InstructorProfile responseDTO = new InstructorProfile();
        responseDTO.setBio(entity.getBio());
        responseDTO.setInstructorId(entity.getInstructorId());
        if (entity.getUser() != null) {
            responseDTO.setFirstName(entity.getUser().getFirstName());
            responseDTO.setLastName(entity.getUser().getLastName());
            responseDTO.setEmail(entity.getUser().getEmail());
            responseDTO.setGender(entity.getUser().getGender());
        }

        if (entity.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDepartmentId(entity.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(entity.getDepartment().getDepartmentName());
            responseDTO.setDepartment(departmentDTO);
        }

        if(entity.getManagedDepartment() != null)
        {
            DepartmentDTO managedDepartmentDTO = new DepartmentDTO();
            managedDepartmentDTO.setDepartmentId(entity.getManagedDepartment().getDepartmentId());
            managedDepartmentDTO.setDepartmentName(entity.getManagedDepartment().getDepartmentName());
            responseDTO.setManagedDepartment(managedDepartmentDTO);
        }

        if (entity.getCourses()!=null&& !entity.getCourses().isEmpty())
        {
            List<CourseDTO> courses = new ArrayList<>();
            for (Course course : entity.getCourses()) {
                CourseDTO dto=new CourseDTO();
                dto.setGrade(course.getYear());
                dto.setCourseId(course.getCourseId());
                dto.setCourseName(course.getCourseName());
                dto.setCourseCode(course.getCourseCode());

                SemesterDTO semesterDTO=new SemesterDTO();
                semesterDTO.setSemesterName(course.getSemester().getSemesterId().getSemesterName().toString());
                semesterDTO.setYear(course.getSemester().getSemesterId().getYearLevel());
                semesterDTO.setYearLevel(course.getSemester().getSemesterId().getYearLevel());
                dto.setSemester(semesterDTO);
                courses.add(dto);
            }
            responseDTO.setCourses(courses);
        }
        responseDTO.setPersonalImage(new FileResponse().getFileName(entity.getPersonalImage()));

        return responseDTO;
    }
}