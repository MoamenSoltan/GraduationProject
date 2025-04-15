package org.example.backend.repository;

import org.example.backend.entity.Student;
import org.example.backend.entity.StudentCourse;
import org.example.backend.entity.StudentCourseId;
import org.example.backend.enums.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {

//    @Query("select sc from StudentCourse sc join  " +
//            "sc.course c" +
//            " where sc.student.studentId=:studentId" +
//            " and (:semesterName is null or sc.semester.semesterId.semesterName = :semesterName)" +
//            " and sc.semester.semesterId.yearLevel=:yearLevel")
//    Optional<StudentCourse> findCoursesWithDegreeByStudentAndSemester(Long studentId, SemesterName semesterName, int yearLevel);

    @Query("select sc from StudentCourse sc join sc.course c " +
            "where sc.student.studentId = :studentId " +
            "and (:semesterName is null or sc.semester.semesterId.semesterName = :semesterName) " +
            "and (:yearLevel is null or sc.semester.semesterId.yearLevel = :yearLevel)")
    List<StudentCourse> findCoursesWithDegreeByStudentAndSemester(
            @Param("studentId") Long studentId,
            @Param("semesterName") SemesterName semesterName,
            @Param("yearLevel") Integer yearLevel);


}
