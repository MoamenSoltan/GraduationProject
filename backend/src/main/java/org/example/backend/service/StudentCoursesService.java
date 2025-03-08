package org.example.backend.service;

import org.example.backend.dto.StudentCourseRequestDTO;
import org.example.backend.entity.*;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.SemesterRepository;
import org.example.backend.repository.StudentCourseRepository;
import org.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentCoursesService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final StudentCourseRepository studentCourseRepository;

    public StudentCoursesService(StudentRepository studentRepository, CourseRepository courseRepository, SemesterRepository semesterRepository, StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public void enrollStudentInCourse(StudentCourseRequestDTO requestDTO) {
        Student student=studentRepository.findById(requestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFound("Student", "id", requestDTO.getStudentId()));;
        SemesterId semesterId = new SemesterId();
        semesterId.setYearLevel(requestDTO.getYearLevel());
        semesterId.setSemesterName(requestDTO.getSemesterName());

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new ResourceNotFound(
                        "Semester",
                        "yearLevel and semesterName",
                        requestDTO.getYearLevel() + " and " + requestDTO.getSemesterName()
                ));

        Course course = courseRepository.findById(requestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFound("Course", "id", requestDTO.getCourseId()));
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(requestDTO.getStudentId());
        id.setCourseId(requestDTO.getCourseId());
        id.setYearLevel(requestDTO.getYearLevel());
        id.setSemesterName(requestDTO.getSemesterName().name());

        StudentCourse studentCourse=new StudentCourse();
        studentCourse.setId(id);
        studentCourse.setCourse(course);
        studentCourse.setStudent(student);
        studentCourse.setSemester(semester);

        studentCourseRepository.save(studentCourse);

    }
}
