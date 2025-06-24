package org.example.backend.service;

import org.example.backend.entity.Student;
import org.example.backend.entity.StudentCourse;
import org.example.backend.enums.LevelYear;
import org.example.backend.enums.SemesterName;
import org.example.backend.repository.StudentCourseRepository;
import org.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public PromotionService(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    /**
     * Checks if a student is eligible for promotion based on their course grades
     * across two semesters (Fall and Spring) of the current academic year.
     *
     * Promotion Rules:
     * - Student must pass at least 4 courses (degree >= 60) across both semesters
     * - If eligible, student is automatically promoted to the next academic year
     * - If not eligible, student remains in the same year
     *
     * @param studentId The ID of the student to check for promotion
     */
    @Transactional
    public void checkAndPromoteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        LevelYear currentYear = student.getAcademicYear();
        int yearLevel = currentYear.ordinal() ; // Convert enum to year level (1-based)

        System.out.println("year level: " + yearLevel);
        System.out.println("current year level: " + currentYear);
        // Get courses for Fall semester of the current year
        List<StudentCourse> fallCourses = studentCourseRepository.findCoursesWithDegreeByStudentAndSemester(
            studentId, SemesterName.Fall, LocalDate.now().getYear()
        );

        // Get courses for Spring semester of the current year
        List<StudentCourse> springCourses = studentCourseRepository.findCoursesWithDegreeByStudentAndSemester(
            studentId, SemesterName.Spring, LocalDate.now().getYear()
        );

        // Count passed courses in Fall semester (degree >= 60)
        long fallPassedCount = fallCourses.stream()
            .filter(sc -> sc.getDegree() != null && sc.getDegree() >= 60)
            .count();

        // Count passed courses in Spring semester (degree >= 60)
        long springPassedCount = springCourses.stream()
            .filter(sc -> sc.getDegree() != null && sc.getDegree() >= 60)
            .count();

        // Total passed courses across both semesters
        long totalPassedCount = fallPassedCount + springPassedCount;

        // Check if student is eligible for promotion (at least 4 courses passed)
        if (totalPassedCount >= 4) {
            LevelYear nextYear = getNextLevelYear(currentYear);
            if (nextYear != null) {
                student.setAcademicYear(nextYear);
                studentRepository.save(student);
                System.out.println("Student " + studentId + " promoted from " + currentYear + " to " + nextYear);
            } else {
                System.out.println("Student " + studentId + " is already in the final year (" + currentYear + ")");
            }
        } else {
            System.out.println("Student " + studentId + " not eligible for promotion. Passed courses: " + totalPassedCount + "/6");
        }
    }

    /**
     * Gets the next academic year level for a given current year.
     *
     * @param current The current academic year
     * @return The next academic year, or null if already in the final year
     */
    private LevelYear getNextLevelYear(LevelYear current) {
        switch (current) {
            case FIRST_YEAR:
                return LevelYear.SECOND_YEAR;
            case SECOND_YEAR:
                return LevelYear.THIRD_YEAR;
            case THIRD_YEAR:
                return LevelYear.FOURTH_YEAR;
            case FOURTH_YEAR:
                return null; // Already in final year
            default:
                throw new IllegalArgumentException("Unknown academic year: " + current);
        }
    }

    /**
     * Alternative promotion method with semester-specific requirements.
     * Student must pass at least 2 courses in Fall AND at least 2 courses in Spring.
     *
     * @param studentId The ID of the student to check for promotion
     */
    @Transactional
    public void checkAndPromoteStudentWithSemesterRequirements(Long studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        LevelYear currentYear = student.getAcademicYear();
        int yearLevel = currentYear.ordinal() + 1;

        // Get courses for both semesters
        List<StudentCourse> fallCourses = studentCourseRepository.findCoursesWithDegreeByStudentAndSemester(
            studentId, SemesterName.Fall, yearLevel
        );

        List<StudentCourse> springCourses = studentCourseRepository.findCoursesWithDegreeByStudentAndSemester(
            studentId, SemesterName.Spring, yearLevel
        );

        // Count passed courses in each semester
        long fallPassedCount = fallCourses.stream()
            .filter(sc -> sc.getDegree() != null && sc.getDegree() >= 60)
            .count();

        long springPassedCount = springCourses.stream()
            .filter(sc -> sc.getDegree() != null && sc.getDegree() >= 60)
            .count();

        // Promote if at least 2 courses passed in Fall AND at least 2 courses passed in Spring
        if (fallPassedCount >= 2 && springPassedCount >= 2) {
            LevelYear nextYear = getNextLevelYear(currentYear);
            if (nextYear != null) {
                student.setAcademicYear(nextYear);
                studentRepository.save(student);
                System.out.println("Student " + studentId + " promoted from " + currentYear + " to " + nextYear);
                System.out.println("Fall passed: " + fallPassedCount + ", Spring passed: " + springPassedCount);
            } else {
                System.out.println("Student " + studentId + " is already in the final year (" + currentYear + ")");
            }
        } else {
            System.out.println("Student " + studentId + " not eligible for promotion.");
            System.out.println("Fall passed: " + fallPassedCount + "/3, Spring passed: " + springPassedCount + "/3");
        }
    }

    /**
     * Batch promotion check for multiple students.
     * Useful for end-of-year processing.
     *
     * @param studentIds List of student IDs to check for promotion
     */
    @Transactional
    public void checkAndPromoteMultipleStudents(List<Long> studentIds) {
        for (Long studentId : studentIds) {
            try {
                checkAndPromoteStudent(studentId);
            } catch (Exception e) {
                System.err.println("Error promoting student " + studentId + ": " + e.getMessage());
            }
        }
    }
}