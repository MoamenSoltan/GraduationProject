
use graduation_project;
CREATE TRIGGER increase_enrollment_count
    AFTER INSERT ON graduation_project.student_course
    FOR EACH ROW
BEGIN
    UPDATE graduation_project.courses
    SET student_enrolled = courses.student_enrolled + 1
    WHERE course_id = NEW.course_id;
END;

CREATE TABLE announcements (
      announcement_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(255) NOT NULL,
      description TEXT,
      type ENUM('EXAM', 'ASSIGNMENT', 'EVENT') NOT NULL,
      course_id INTEGER NOT NULL,
      instructor_id INTEGER,
      announcement_date DATETIME NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
     FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id) ON DELETE SET NULL
);