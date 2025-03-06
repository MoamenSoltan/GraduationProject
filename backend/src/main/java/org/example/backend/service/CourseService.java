package org.example.backend.service;

import org.example.backend.dto.CourseRequestDTO;
import org.example.backend.dto.CourseResponseDTO;
import org.example.backend.entity.Course;
import org.example.backend.entity.Department;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Semester;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.CourseMapper;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.DepartmentRepository;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final DepartmentRepository departmentRepository;
    private final InstructorRepository instructorRepository;
    private final SemesterRepository semesterRepository;

    public CourseService(CourseRepository courseRepo, DepartmentRepository departmentRepository, InstructorRepository instructorRepository, SemesterRepository semesterRepository) {
        this.courseRepo = courseRepo;
        this.departmentRepository = departmentRepository;
        this.instructorRepository = instructorRepository;
        this.semesterRepository = semesterRepository;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO dto)
    {
        Course course = CourseMapper.toEntity(dto);

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFound("Department", "id", dto.getDepartmentId()));
        course.setDepartment(department);

        Semester semester =semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() -> new ResourceNotFound("Semester", "id", dto.getSemesterId()));

        course.setSemester(semester);

        if(dto.getInstructorId()!=null)
        {
            Instructor instructor = instructorRepository.findById(dto.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFound("Instructor", "id", dto.getInstructorId()));
            course.setInstructor(instructor);
        }

        if (dto.getPrerequisiteCourseId() != null) {
            Course prerequisiteCourse = courseRepo.findById(dto.getPrerequisiteCourseId())
                   .orElseThrow(() -> new ResourceNotFound("Course", "id", dto.getPrerequisiteCourseId()));
            course.setPrerequisiteCourse(prerequisiteCourse);
        }

        Course savedCourse = courseRepo.save(course);
        return CourseMapper.toResponseDTO(savedCourse);

    }

    public List<CourseResponseDTO> getAllCourses()
    {
        List<Course> courses = courseRepo.findAll();


        return courses.stream()
                .map(CourseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
