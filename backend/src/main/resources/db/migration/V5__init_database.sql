
use graduation_project;
CREATE TRIGGER increase_enrollment_count
    AFTER INSERT ON graduation_project.student_course
    FOR EACH ROW
BEGIN
    UPDATE graduation_project.courses
    SET student_enrolled = courses.student_enrolled + 1
    WHERE course_id = NEW.course_id;
END;