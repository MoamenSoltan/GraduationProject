package org.example.backend.service;

import org.example.backend.dto.StudentCourseRequestDTO;
import org.example.backend.dto.courseDto.DegreeCourseDTO;
import org.example.backend.entity.*;
import org.example.backend.enums.SemesterName;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.SemesterRepository;
import org.example.backend.repository.StudentCourseRepository;
import org.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public void enrollStudentInCourse(StudentCourseRequestDTO requestDTO,Student student) {
//        Student student=studentRepository.findById(requestDTO.getStudentId())
//                .orElseThrow(() -> new ResourceNotFound("Student", "id", requestDTO.getStudentId()));;
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
        id.setStudentId(student.getStudentId());
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

    public List<DegreeCourseDTO> getCoursesWithDegreeByStudentAndSemester(String email,SemesterName semesterName, Integer yearLevel) {
        // Step 1: Find the student by email
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Student", "email", email));
        Long studentId = student.getStudentId();

        // Step 2: Retrieve all StudentCourse objects for the student
        List<StudentCourse> studentCourses = studentCourseRepository.findCoursesWithDegreeByStudentAndSemester(studentId,semesterName, yearLevel);

        if (studentCourses.isEmpty()) {
            throw new ResourceNotFound("StudentCourse", "studentId", studentId);
        }

        // Step 3: Group courses by semester
        Map<String, DegreeCourseDTO> semesterMap = new LinkedHashMap<>();

        for (StudentCourse sc : studentCourses) {
            String semesterKey = sc.getSemester().getSemesterId().getSemesterName() + "-" + sc.getSemester().getSemesterId().getYearLevel();

            // Create or retrieve the semester DTO
            DegreeCourseDTO semesterDTO = semesterMap.computeIfAbsent(semesterKey, k -> {
                DegreeCourseDTO newSemester = new DegreeCourseDTO();
                newSemester.setSemesterName(sc.getSemester().getSemesterId().getSemesterName());
                newSemester.setSemesterYear(sc.getSemester().getSemesterId().getYearLevel());
                newSemester.setCourses(new ArrayList<>());
                return newSemester;
            });

            // Add the course to the semester
            DegreeCourseDTO.Course course = new DegreeCourseDTO.Course();
            course.setCourseCode(sc.getCourse().getCourseCode());
            course.setCourseName(sc.getCourse().getCourseName());
            course.setHours(sc.getCourse().getCredit());
            course.setDegree(sc.getDegree());
            semesterDTO.getCourses().add(course);
        }

        // Step 4: Calculate GPA for each semester
        for (DegreeCourseDTO semester : semesterMap.values()) {
            calculateGPA(semester);
        }

        // Step 5: Return the list of semesters
        return new ArrayList<>(semesterMap.values());
    }

    private void calculateGPA(DegreeCourseDTO semester) {
        double totalGradeCredits = 0;
        double totalHours = 0;

        for (DegreeCourseDTO.Course course : semester.getCourses()) {
            totalHours += course.getHours();
            if (course.getDegree() != null) {
                totalGradeCredits += course.getDegree() * course.getHours();
            }
        }

        double gpa = totalHours > 0 ? totalGradeCredits / totalHours : 0;
        semester.setGpa(gpa);
    }



}
