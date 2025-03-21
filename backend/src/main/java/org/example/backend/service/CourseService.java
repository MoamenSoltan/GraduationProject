package org.example.backend.service;

import jakarta.validation.Valid;
import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.courseDto.CourseRequestDTO;
import org.example.backend.dto.courseDto.CourseResponseDTO;
import org.example.backend.entity.*;
import org.example.backend.enums.LevelYear;
import org.example.backend.enums.SemesterName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.CourseMapper;
import org.example.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final DepartmentRepository departmentRepository;
    private final InstructorRepository instructorRepository;
    private final SemesterRepository semesterRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepo, DepartmentRepository departmentRepository, InstructorRepository instructorRepository, SemesterRepository semesterRepository, StudentRepository studentRepository) {
        this.courseRepo = courseRepo;
        this.departmentRepository = departmentRepository;
        this.instructorRepository = instructorRepository;
        this.semesterRepository = semesterRepository;
        this.studentRepository = studentRepository;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO dto)
    {
        Course course = CourseMapper.toEntity(dto);

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFound("Department", "id", dto.getDepartmentId()));
        course.setDepartment(department);

        SemesterId semesterId = new SemesterId();
        semesterId.setYearLevel(dto.getYearLevel());
        semesterId.setSemesterName(dto.getSemesterName());

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new ResourceNotFound(
                        "Semester",
                        "yearLevel and semesterName",
                        dto.getYearLevel() + " and " + dto.getSemesterName()
                ));
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

    public String deleteCourse(Long id) {
        Course course = courseRepo.findById(id)
                        .orElseThrow(()-> new ResourceNotFound("course", "id", id));
        courseRepo.delete(course);

        return "Course with id " + id + " deleted successfully";
    }

    @Transactional
    public CourseResponseDTO updateCourse(@Valid CourseRequestDTO courseDTO) {
        Course existingCourse = courseRepo.findByCode(courseDTO.getCourseCode())
                .orElseThrow(() -> new ResourceNotFound("Course", "id", courseDTO.getCourseCode()));

        // Step 2: Update basic course attributes
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setCourseCode(courseDTO.getCourseCode());
        existingCourse.setCredit(courseDTO.getCredit());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setMaxStudents(courseDTO.getMaxStudents());
        existingCourse.setSchedule(courseDTO.getSchedule());
        existingCourse.setType(courseDTO.getType());
        existingCourse.setYear(courseDTO.getYear());

        // Step 3: Update department if provided
        if (courseDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(courseDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFound("Department", "id", courseDTO.getDepartmentId()));
            existingCourse.setDepartment(department);
        }

        // Step 4: Update semester if provided
        if (courseDTO.getYearLevel() != null && courseDTO.getSemesterName() != null) {
            SemesterId semesterId = new SemesterId();
            semesterId.setYearLevel(courseDTO.getYearLevel());
            semesterId.setSemesterName(courseDTO.getSemesterName());

            Semester semester = semesterRepository.findById(semesterId)
                    .orElseThrow(() -> new ResourceNotFound("Semester", "yearLevel and semesterName",
                            courseDTO.getYearLevel() + " and " + courseDTO.getSemesterName()));
            existingCourse.setSemester(semester);
        }

        // Step 5: Update instructor if provided
        if (courseDTO.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(courseDTO.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFound("Instructor", "id", courseDTO.getInstructorId()));
            existingCourse.setInstructor(instructor);
        }

        // Step 6: Update prerequisite course if provided
        if (courseDTO.getPrerequisiteCourseId() != null) {
            Course prerequisiteCourse = courseRepo.findById(courseDTO.getPrerequisiteCourseId())
                    .orElseThrow(() -> new ResourceNotFound("Course", "id", courseDTO.getPrerequisiteCourseId()));
            existingCourse.setPrerequisiteCourse(prerequisiteCourse);
        }

        // Step 7: Save updated course
        Course updatedCourse = courseRepo.save(existingCourse);

        return CourseMapper.toResponseDTO(updatedCourse);
    }

    @Transactional
    public CourseResponseDTO updateCourse(Long id , CourseRequestDTO  courseDTO)
    {
        Course existingCourse = courseRepo.findById(id)

                .orElseThrow(() -> new ResourceNotFound("Course", "id", id));

        // Step 2: Update basic course attributes
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setCourseCode(courseDTO.getCourseCode());
        existingCourse.setCredit(courseDTO.getCredit());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setMaxStudents(courseDTO.getMaxStudents());
        existingCourse.setSchedule(courseDTO.getSchedule());
        existingCourse.setType(courseDTO.getType());
        existingCourse.setYear(courseDTO.getYear());

        // Step 3: Update department if provided
        if (courseDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(courseDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFound("Department", "id", courseDTO.getDepartmentId()));
            existingCourse.setDepartment(department);
        }

        // Step 4: Update semester if provided
        if (courseDTO.getYearLevel() != null && courseDTO.getSemesterName() != null) {
            SemesterId semesterId = new SemesterId();
            semesterId.setYearLevel(courseDTO.getYearLevel());
            semesterId.setSemesterName(courseDTO.getSemesterName());

            Semester semester = semesterRepository.findById(semesterId)
                    .orElseThrow(() -> new ResourceNotFound("Semester", "yearLevel and semesterName",
                            courseDTO.getYearLevel() + " and " + courseDTO.getSemesterName()));
            existingCourse.setSemester(semester);
        }

        // Step 5: Update instructor if provided
        if (courseDTO.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(courseDTO.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFound("Instructor", "id", courseDTO.getInstructorId()));
            existingCourse.setInstructor(instructor);
        }

        // Step 6: Update prerequisite course if provided
        if (courseDTO.getPrerequisiteCourseId() != null) {
            Course prerequisiteCourse = courseRepo.findById(courseDTO.getPrerequisiteCourseId())
                    .orElseThrow(() -> new ResourceNotFound("Course", "id", courseDTO.getPrerequisiteCourseId()));
            existingCourse.setPrerequisiteCourse(prerequisiteCourse);
        }

        // Step 7: Save updated course
        Course updatedCourse = courseRepo.save(existingCourse);

        return CourseMapper.toResponseDTO(updatedCourse);
    }

    public CourseResponseDTO assignCourseToSemester(Integer yearLevel, SemesterName semesterName, Long courseId) {
        SemesterId semesterId = new SemesterId();
        semesterId.setYearLevel(yearLevel);
        semesterId.setSemesterName(semesterName);
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new ResourceNotFound("Semester", "yearLevel and semesterName",
                        yearLevel + " and " + semesterName));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFound("Course", "id", courseId));

        course.setSemester(semester);


        Course updatedCourse=courseRepo.save(course);

        return CourseMapper.toResponseDTO(updatedCourse);

    }

    public List<CourseResponseDTO> getCoursesForRegistration(String email) {
        Student student=studentRepository.findStudentByEmail(email).get();
        List<Course> courses =courseRepo.getCourseByYear(student.getAcademicYear()).get();
        List<CourseResponseDTO> courseDTO = new ArrayList<>();

        for (var i:courses)
        {
           courseDTO.add( CourseMapper.toResponseDTO(i));
        }
        return courseDTO;

    }

    public List<CourseResponseDTO> getCoursesCompletedByStudent(String studentEmail, LevelYear year) {
        List<Course> completedCourses = courseRepo.getCoursesByStudentAndYear(studentEmail, year);
        List<CourseResponseDTO> dtos=new ArrayList<>();
        for (var i:completedCourses)
        {
            dtos.add(CourseMapper.toResponseDTO(i));
        }

        return dtos;
    }
}
