package org.example.backend.mapper;

import org.example.backend.dto.*;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.courseDto.CourseRequestDTO;
import org.example.backend.dto.courseDto.CourseResponseDTO;
import org.example.backend.dto.instructorDto.InstructorDTO;
import org.example.backend.dto.semesterDto.SemesterDTO;
import org.example.backend.entity.Course;
import org.example.backend.enums.CourseType;
import org.example.backend.enums.LevelYear;

public class CourseMapper {
    public static Course toEntity(CourseRequestDTO dto)
    {
        Course course = new Course();
        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getCourseName());
        course.setDescription(dto.getDescription());
        course.setCredit(dto.getCredit());
        course.setMaxStudents(dto.getMaxStudents());
        course.setSchedule(dto.getSchedule());


        if (dto.getYear() != null) {
            course.setYear(LevelYear.valueOf(dto.getYear().name()));
        }
        if (dto.getType() != null) {
            course.setType(CourseType.valueOf(dto.getType().name()));
        }


        return course;
    }

    public static CourseResponseDTO toResponseDTO(Course entity)
    {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setCourseId(entity.getCourseId());
        dto.setCourseName(entity.getCourseName());
        dto.setCourseCode(entity.getCourseCode());
        dto.setCredit(entity.getCredit());
        dto.setDescription(entity.getDescription());
        dto.setMaxStudents(entity.getMaxStudents());

        if (entity.getYear() != null) {
            dto.setYear(entity.getYear());
        }
        if (entity.getType() != null) {
            dto.setType(entity.getType());
        }


        dto.setSchedule(entity.getSchedule());
        dto.setStudentEnrolled(entity.getStudentEnrolled());
        dto.setCreatedAt(entity.getCreatedAt());

        if(entity.getInstructor()!=null){
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setInstructorId(entity.getInstructor().getInstructorId());
            instructorDTO.setFirstName(entity.getInstructor().getUser().getFirstName());
            instructorDTO.setLastName(entity.getInstructor().getUser().getLastName());
            instructorDTO.setEmail(entity.getInstructor().getUser().getEmail());

            dto.setInstructor(instructorDTO);
        }

        if(entity.getDepartment()!=null)
        {
            DepartmentDTO  departmentDTO =new DepartmentDTO();
            departmentDTO.setDepartmentId(entity.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(entity.getDepartment().getDepartmentName());

            dto.setDepartment(departmentDTO);
        }

        if (entity.getPrerequisiteCourse()!=null)
        {
            SemesterDTO semesterDTO=new SemesterDTO();
            semesterDTO.setYearLevel(entity.getSemester().getSemesterId().getYearLevel());
            semesterDTO.setSemesterName(String.valueOf(entity.getSemester().getSemesterId().getSemesterName()));
            semesterDTO.setYear(entity.getSemester().getStartDate().getYear());

            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseId(entity.getPrerequisiteCourse().getCourseId());
            courseDTO.setCourseName(entity.getPrerequisiteCourse().getCourseName());
            courseDTO.setCourseCode(entity.getPrerequisiteCourse().getCourseCode());
            courseDTO.setSemester(semesterDTO);
            courseDTO.setGrade(entity.getYear());
//            courseDTO.setSemester();
            dto.setPrerequisiteCourse(courseDTO);
        }

        if (entity.getSemester() != null) {
            SemesterDTO semesterDTO=new SemesterDTO();
            semesterDTO.setYearLevel(entity.getSemester().getSemesterId().getYearLevel());
            semesterDTO.setSemesterName(String.valueOf(entity.getSemester().getSemesterId().getSemesterName()));
            semesterDTO.setYear(entity.getSemester().getStartDate().getYear());
            dto.setSemester(semesterDTO);
        }

        return dto;
    }
}
