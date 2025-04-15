package org.example.backend.dto.courseDto;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.SemesterName;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DegreeCourseDTO {
    private SemesterName semesterName;
    private int semesterYear;
    private List<Course> courses = new ArrayList<>();
    private Double gpa;

    public DegreeCourseDTO() {
    }

    public DegreeCourseDTO(SemesterName semesterName, int semesterYear, String courseCode, String courseName, Double degree, int hours) {
        this.semesterName = semesterName;
        this.semesterYear = semesterYear;
        this.courses.add(new Course(courseCode, courseName, hours, degree));
    }

    @Setter
    @Getter
    public static class Course {
        private String courseCode;
        private String courseName;
        private int hours;
        private Double degree;
        public Course(){}

        public Course(String courseCode, String courseName, int hours, Double degree) {
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.hours = hours;
            this.degree = degree;
        }
    }
}