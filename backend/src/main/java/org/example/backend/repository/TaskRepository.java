package org.example.backend.repository;

import org.example.backend.entity.Course;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Query("select t from Task t where t.course.courseId =:courseId")
    List<Task> findTaskByCourseId(@Param("courseId") int courseId);

    @Query("SELECT t FROM Task t WHERE t.course.instructor = :instructor AND (:courseId IS NULL OR t.course.courseId = :courseId) ORDER BY t.id desc "
            )
    List<Task> findTaskByInstructorDesc(@Param("instructor") Instructor instructor,
                                    @Param("courseId") Long courseId);

    @Query("SELECT t FROM Task t WHERE t.course.instructor = :instructor AND (:courseId IS NULL OR t.course.courseId = :courseId) ORDER BY t.id asc "
    )
    List<Task> findTaskByInstructorAsc(@Param("instructor") Instructor instructor,
                                    @Param("courseId") Long courseId);

    List<Task> findByCourseInstructorAndCourseCourseId(Instructor instructor, Long courseId, Sort sort);


    @Query(value = "SELECT distinct t.* " +
            "FROM tasks t " +
            "JOIN courses c ON t.course_id = c.course_id " +
            "JOIN student_course sc ON c.course_id = sc.course_id " +
            "JOIN students s ON sc.student_id = s.student_id " +
            "JOIN users u ON s.user_id = u.id " +
            "WHERE u.email = :email " +
            "  AND t.deadline > :currentDate " +
            "ORDER BY t.deadline ASC", nativeQuery = true)
    List<Task> findUpcomingDeadlinesForStudent(@Param("email") String email, @Param("currentDate") Date currentDate);

    @Query(value = "SELECT DISTINCT  t.* " +
            "FROM tasks t " +
            "JOIN courses c ON t.course_id = c.course_id " +
            "JOIN student_course sc ON c.course_id = sc.course_id " +
            "JOIN students s ON sc.student_id = s.student_id " +
            "JOIN users u ON s.user_id = u.id " +
            "WHERE u.email = :email " +
            "  AND t.deadline > :currentDate and c.course_id=:courseId " +
            "ORDER BY t.deadline ASC", nativeQuery = true)
    List<Task> findUpcomingDeadlinesForStudent(@Param("email") String email, @Param("currentDate") Date currentDate,@Param("courseId") Long courseId);

    @Query(value = "SELECT distinct t.* FROM tasks t " +
            "JOIN courses c ON t.course_id = c.course_id " +
            "JOIN student_course sc ON c.course_id = sc.course_id " +
            "JOIN students s ON sc.student_id = s.student_id " +
            "JOIN users u ON s.user_id = u.id " +
            "WHERE u.email = :email " +
            "AND t.deadline < :currentDate " +
            "AND t.is_completed = false " +
            "ORDER BY t.deadline ASC", nativeQuery = true)
    List<Task> findPastDeadlinesForStudent(String email, Date currentDate);


    @Query("select t from Task t " +
            "join fetch StudentCourse  sc on sc.course=t.course " +
            "where t.course.courseId=:courseId and" +
            " t.deadline >:currentDate and sc.student.user.email=:email")
    List<Task> findUpcomingDeadlinesForStudentAndCourse(@Param("email") String email, @Param("currentDate") LocalDate currentDate, @Param("courseId") Long courseId);

    @Query("select t from Task t " +
            "join fetch StudentCourse  sc on sc.course=t.course " +
            "where t.course.courseId=:courseId and" +
            " t.deadline <:currentDate and sc.student.user.email=:email")
    List<Task> findPastDeadlinesForStudent(@Param("email") String email, @Param("currentDate") LocalDate currentDate, @Param("courseId") Long courseId);


    @Query("select t from Task t where t.course=?1 and t.id" +
            " =?2")
    Optional<Task> findTaskByCourseIdAndTaskId(Course course, int taskId);
}
