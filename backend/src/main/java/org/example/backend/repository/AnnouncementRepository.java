package org.example.backend.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.backend.entity.Announcement;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Student;
import org.example.backend.enums.AnnouncementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    @Query("select a from Announcement a where a.type=:type or :type is null ")
     List<Announcement> getAllAnnouncements(@Param("type") AnnouncementType type);

    List<Announcement> findByInstructor(Instructor instructor);

    @Query("SELECT DISTINCT a FROM Announcement a " +
            "JOIN a.course c " +
            "JOIN StudentCourse sc ON sc.course = c " +
            "WHERE sc.student = :student " +
            "ORDER BY a.announcementDate DESC")
    List<Announcement> findAnnouncementsForStudent(@Param("student") Student student);

    @Query("SELECT DISTINCT a FROM Announcement a " +
            "JOIN a.course c " +
            "JOIN StudentCourse sc ON sc.course = c " +
            "WHERE sc.student = :student " +
            "AND (a.type = :type OR :type IS NULL) " +
            "ORDER BY a.announcementDate DESC")
    List<Announcement> findAnnouncementsForStudentByType(@Param("student") Student student, @Param("type") AnnouncementType type);
//
}
