package org.example.backend.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor,Integer> {
    @Query("select i from Instructor i where i.user.email=:email")
    Optional<Instructor> getByEmail(@Param("email") String email);

    Optional<Instructor> findByUser(User user);

    @Modifying
    @Query(value = "DELETE FROM user_roles ur WHERE ur.user_id = :userId" ,nativeQuery = true)
    void deleteByUserId(@Param("userId") int userId);

    @Query("select i from Instructor i " +
            "join Course c on i.instructorId=c.instructor.instructorId " +
            "join StudentCourse sc on sc.course.courseId =c.courseId " +
            "join Student s on s.studentId = sc.student.studentId " +
            "join User u_st on s.user.id = u_st.id " +
            "join User u_in on s.user.id = u_in.id " +
            "where u_st.email=:studentEmail"
    )
    Optional<Instructor> getCoursesInstructorForStudent(@Param("studentEmail") String studentEmail);


    boolean existsByUserEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 100, message = "Email must not exceed 100 characters") String email);
}
