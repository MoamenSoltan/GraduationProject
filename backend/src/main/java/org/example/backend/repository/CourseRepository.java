package org.example.backend.repository;

import org.example.backend.dto.courseDto.DegreeCourseDTO;
import org.example.backend.entity.Course;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Semester;
import org.example.backend.enums.LevelYear;
import org.example.backend.enums.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("select c from Course c where c.courseCode=:code")
    Optional<Course> findByCode(@Param("code") String code);

    Optional<List<Course>> findByInstructor(Instructor instructor);
    @Query("SELECT c FROM Course c WHERE c.instructor = :instructor AND c.courseId = :courseId")
    Optional<Course> findCourseByInstructorAndCourseId(Instructor instructor, Long courseId);

    @Modifying
    @Query("UPDATE Course c SET c.instructor = NULL WHERE c.instructor.user.id = :instructorId")
    void removeInstructorFromCourses(@Param("instructorId") int instructorId);

    Optional<List<Course>> getCourseByYear(@Param("year") LevelYear year);

    @Query("SELECT c " +
            "FROM Student st " +
            "JOIN st.user u " +
            "JOIN st.studentCourse sc " +
            "JOIN sc.course c " +
            "WHERE u.email = :email " +
            "AND (c.year = :year OR :year IS NULL)")
    List<Course> getCoursesByStudentAndYear(@Param("email") String email, @Param("year") LevelYear year);

    @Query("select new org.example.backend.dto.courseDto.DegreeCourseDTO(sc.semester.semesterId.semesterName, s.semesterId.yearLevel, c.courseCode, c.courseName, sc.degree,c.credit)" +
            "from Semester s join Course c on s.semesterId=c.semester.semesterId " +
            "join StudentCourse sc on c.courseId=sc.course.courseId " +
            "where sc.semester.semesterId.semesterName=:semesterName and s.semesterId.yearLevel=:semesterYear and sc.student.studentId=:studentId " +
            "and sc.student.academicYear=:academicYear")

    List<DegreeCourseDTO> getDegreeCourses(@Param("academicYear") LevelYear academicYear, @Param("semesterName") SemesterName semesterName, @Param("semesterYear") int semesterYear, @Param("studentId") Long studentId);

    @Query("select new org.example.backend.dto.courseDto.DegreeCourseDTO(s.semesterId.semesterName, s.semesterId.yearLevel, c.courseCode, c.courseName, sc.degree,c.credit)" +
            "from Semester s join Course c on s.semesterId=c.semester.semesterId " +
            "join StudentCourse sc on c.courseId=sc.course.courseId " +
            "where  s.semesterId.yearLevel=:semesterYear and sc.student.studentId=:studentId " +
            "and sc.student.academicYear=:academicYear")

    List<DegreeCourseDTO> getDegreeCourses(@Param("academicYear") LevelYear academicYear, @Param("semesterYear") int semesterYear, @Param("studentId") Long studentId);

    @Query("select new org.example.backend.dto.courseDto.DegreeCourseDTO(sc.semester.semesterId.semesterName, s.semesterId.yearLevel, c.courseCode, c.courseName, sc.degree, c.credit) " +
            "from Semester s join Course c on s.semesterId = c.semester.semesterId " +
            "join StudentCourse sc on c.courseId = sc.course.courseId " +
            "where sc.student.studentId = :studentId " +
            "order by s.semesterId.yearLevel, s.semesterId.semesterName")
    List<DegreeCourseDTO> getAllSemestersForStudent(@Param("studentId") Long studentId);

    @Query("SELECT count(c)>0 FROM Course c WHERE c.courseId = :courseId AND c.instructor.user.email = :instructorEmail")
    boolean isInstructorOfCourse(@Param("instructorEmail") String instructorEmail,@Param("courseId") Long courseId);


    @Query("select c from Course c where c.year=:academicYear and c.semester=:semester ")
    List<Course> getCoursesBySemesterAndYearAcademic(@Param("academicYear") LevelYear academicYear,@Param("semester") Semester semester);
}
