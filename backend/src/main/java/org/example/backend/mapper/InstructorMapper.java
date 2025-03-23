package org.example.backend.mapper;

import org.example.backend.dto.DepartmentDTO;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.instructorDto.InstructorProfile;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.semesterDto.SemesterDTO;
import org.example.backend.entity.*;
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

    public static InstructorResponseDTO entityToResponseDTO(Instructor entity, Student student) {
        InstructorResponseDTO responseDTO = new InstructorResponseDTO();

        // Set the personal image URL
        responseDTO.setPersonalImage(new FileResponse().getFileName(entity.getPersonalImage()));

        // Set basic instructor details
        responseDTO.setInstructorId(entity.getInstructorId());
        if (entity.getUser() != null) {
            responseDTO.setFirstName(entity.getUser().getFirstName());
            responseDTO.setLastName(entity.getUser().getLastName());
            responseDTO.setEmail(entity.getUser().getEmail());
            responseDTO.setGender(entity.getUser().getGender());
        }

        // Set department information
        if (entity.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDepartmentId(entity.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(entity.getDepartment().getDepartmentName());
            responseDTO.setDepartment(departmentDTO);
        }

        // Set managed department information
        if (entity.getManagedDepartment() != null) {
            DepartmentDTO managedDepartmentDTO = new DepartmentDTO();
            managedDepartmentDTO.setDepartmentId(entity.getManagedDepartment().getDepartmentId());
            managedDepartmentDTO.setDepartmentName(entity.getManagedDepartment().getDepartmentName());
            responseDTO.setManagedDepartment(managedDepartmentDTO);
        }

        // Filter and include only the courses that this student is enrolled in
        if (entity.getCourses() != null && !entity.getCourses().isEmpty()) {
            List<CourseDTO> courses = new ArrayList<>();

            for (Course course : entity.getCourses()) {
                // Check if the student is enrolled in this course with the current instructor
                boolean studentEnrolled = false;
                for (StudentCourse studentCourse : student.getStudentCourse()) {
                    if (studentCourse.getCourse().getCourseId().equals(course.getCourseId())) {
                        studentEnrolled = true;
                        break;
                    }
                }

                // If the student is enrolled in this course, add it to the response
                if (studentEnrolled) {
                    CourseDTO dto = new CourseDTO();
                    dto.setGrade(course.getYear()); // Assuming `course.getYear()` represents the grade
                    dto.setCourseId(course.getCourseId());
                    dto.setCourseName(course.getCourseName());
                    dto.setCourseCode(course.getCourseCode());

                    // Set semester details
                    SemesterDTO semesterDTO = new SemesterDTO();
                    semesterDTO.setSemesterName(course.getSemester().getSemesterId().getSemesterName().toString());
                    semesterDTO.setYear(course.getSemester().getSemesterId().getYearLevel());
                    semesterDTO.setYearLevel(course.getSemester().getSemesterId().getYearLevel());
                    dto.setSemester(semesterDTO);

                    courses.add(dto);
                }
            }
            responseDTO.setCourses(courses);
        }

        return responseDTO;
    }

}