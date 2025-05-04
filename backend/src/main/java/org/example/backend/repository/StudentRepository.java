package org.example.backend.repository;

import jakarta.transaction.Transactional;
import org.example.backend.dto.studentDto.StudentCourseDTO;
import org.example.backend.entity.Student;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {


    Optional<Student> findByUser(User user);
    @Query("select s from Student s where s.user.email=:email")
    Optional<Student> findStudentByEmail(@Param("email") String email);

    @Query("select new org.example.backend.dto.studentDto.StudentCourseDTO(s.studentId,concat( s.user.firstName ,'',s.user.lastName), s.user.email, sc.degree) " +
            "from Student s " +
            "join StudentCourse sc on s.studentId = sc.student.studentId " +
            "join Course c on sc.course.courseId = c.courseId " +
            "where sc.course.courseId = :courseId and c.instructor.instructorId = :instructorId")
    Optional<List<StudentCourseDTO>> getStudentInCourse(@Param("courseId") int courseId, @Param("instructorId") int instructorId);

    @Query("select new org.example.backend.dto.studentDto.StudentCourseDTO(s.studentId,concat( s.user.firstName ,'',s.user.lastName), s.user.email, sc.degree) " +
            "from Student s " +
            "join StudentCourse sc on s.studentId = sc.student.studentId " +
            "join Course c on sc.course.courseId = c.courseId " +
            "where sc.course.courseId = :courseId and c.instructor.instructorId = :instructorId and s.studentId = :studentId")
    Optional<StudentCourseDTO> getStudentInCourse(@Param("courseId") int courseId, @Param("instructorId") int instructorId, @Param("studentId") Long studentId);

    @Query("update StudentCourse sc  " +
            " set " +
            "sc.degree = :degree  where " +
            "sc.student.studentId =:studentId and sc.course.courseId=:courseId " )
    @Modifying
    @Transactional
    void updateStudentDegree(@Param("studentId") Long studentId, @Param("degree") Double degree, @Param("courseId") int courseId);

    @Query("select s from Student s join StudentCourse sc on s.studentId = sc.student.studentId" +
            " join Course c on sc.course.courseId = c.courseId " +
            "where c.courseId=:courseId" )
    List<Student> getStudentsInCourse(@Param("courseId") Long courseId);
}
