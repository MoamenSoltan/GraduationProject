use graduation_project;
create table roles(
                      id int primary key auto_increment not null,
                      role_name enum('ADMIN','STUDENT','INSTRUCTOR') default 'STUDENT'
);


CREATE TABLE submission_request (
                                    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
                                    `first_name` VARCHAR(255) NOT NULL,
                                    `last_name` VARCHAR(255) NOT NULL,
                                    `email` VARCHAR(255) UNIQUE NOT NULL,
                                    `password` VARCHAR(255) NOT NULL,
                                    `user_type` ENUM('STUDENT', 'INSTRUCTOR') DEFAULT 'STUDENT',
                                    `academic_year` INTEGER DEFAULT 1,
                                    `high_school_name` VARCHAR(255),
                                    `graduation_year` VARCHAR(255),
                                    `high_school_gpa` DOUBLE,
                                    `high_school_certificate` VARCHAR(255),
                                    `phone_number` VARCHAR(15),
                                    `id_photo` VARCHAR(255),
                                    `personal_photo` VARCHAR(255),
                                    `country` VARCHAR(255),
                                    `city` VARCHAR(255),
                                    `address` VARCHAR(255),
                                    `admission_status` ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING'
                                    ,gender ENUM('MALE', 'FEMALE'),
                                    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table users
(
    id  int primary key auto_increment not null,
    first_name varchar(255)    not null,
    last_name  varchar(255)    not null,
    gender ENUM('MALE','FEMALE') ,
    email      varchar(255)    not null unique,
    password   varchar(255)    not null

);

CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,

                            primary key (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

create table department
(
    department_id int primary key auto_increment,
    department_name enum('CS','IT','IS','general') default 'general',
    head_of_department_id int ,
    created_at timestamp

);

create table students(
                         student_id int primary key auto_increment,
                         user_id int  ,
                         academic_year int,
                         gpa double,
                         department_id int,
                         fees_status enum('paid','pending'),
                         created_at timestamp ,
                         submission_request_id int ,
                         foreign key (submission_request_id) references submission_request(id),
                         foreign key (department_id) references department(department_id),
                         foreign key (user_id) references users(id)

);

CREATE table instructors(
                            instructor_id int primary key auto_increment,
                            user_id int  ,
                            department_id int,
                            created_at timestamp ,
                            foreign key (department_id) references department(department_id) on delete set null ,
                            foreign key (user_id) references users(id) on delete cascade
);

ALTER TABLE department
    ADD CONSTRAINT fk_head_of_department
        FOREIGN KEY (head_of_department_id) REFERENCES instructors(instructor_id) ON DELETE SET NULL;


CREATE TABLE `semester` (
                            `year_level` INTEGER NOT NULL,
                            `semester_name` ENUM('Fall', 'Spring', 'Summer') NOT NULL,
                            `start_date` DATE NOT NULL,
                            `end_date` DATE NOT NULL,
                            `is_active` BOOLEAN DEFAULT FALSE,
                            `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (year_level, semester_name)
);

CREATE TABLE `courses` (
                           `course_id` INT PRIMARY KEY AUTO_INCREMENT,
                           `course_name` VARCHAR(255) NOT NULL,
                           `course_code` VARCHAR(255) unique NOT NULL,
                           `credit` INT,
                           `department_id` INT,
                           `semester_year_level` INTEGER NOT NULL,
                           `semester_name` ENUM('Fall', 'Spring', 'Summer') NOT NULL,
                           `instructor_id` INT,
                           `prerequisites_course_id` INT,
                           `course_description` TEXT,
                           `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (`department_id`) REFERENCES `department`(`department_id`),
                           FOREIGN KEY (`semester_year_level`, `semester_name`) REFERENCES `semester`(`year_level`, `semester_name`),
                           FOREIGN KEY (`instructor_id`) REFERENCES `instructors`(`instructor_id`),
                           FOREIGN KEY (`prerequisites_course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `student_course` (
                                  `student_id` INTEGER NOT NULL,
                                  `course_id` INTEGER NOT NULL,
                                  `semester_year_level` INTEGER NOT NULL,
                                  `semester_name` ENUM('Fall', 'Spring', 'Summer') NOT NULL,
                                  `degree` DOUBLE,
                                  `enrollment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`student_id`, `course_id`, `semester_year_level`, `semester_name`),
                                  FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE,
                                  FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE,
                                  FOREIGN KEY (`semester_year_level`, `semester_name`) REFERENCES `semester`(`year_level`, `semester_name`) ON DELETE CASCADE
);




insert into graduation_project.roles(id, role_name) values (1, 'ADMIN'),
                                                           (2, 'INSTRUCTOR'),
                                                           (3, 'STUDENT');


INSERT INTO users (first_name, last_name, gender, email, password)
VALUES ('Admin', 'User', 'MALE', 'admin@example.com', '$2a$10$rp/QTc5rfYAUCPanwoyXE.hXrzauN7qa4M7xGBM/VucPj3blvUJNa');

insert into user_roles values (1,1);

insert into graduation_project.department values (1,'general',null,now()),
                                                 (2,'CS',null,now()),
                                                 (3,'IT',null,now()),
                                                 (4,'IS',null,now());

CREATE TABLE refresh_tokens (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                token VARCHAR(512) NOT NULL,
                                user_id int NOT NULL,
                                expiry_date TIMESTAMP NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT FALSE,
                                CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                CONSTRAINT uk_token UNIQUE (token)
);


CREATE INDEX idx_refresh_token_user_id ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_token_expiry ON refresh_tokens(expiry_date);

ALTER TABLE `courses` DROP FOREIGN KEY `courses_ibfk_3`;

ALTER TABLE `courses`
    ADD CONSTRAINT `courses_ibfk_3` FOREIGN KEY (`instructor_id`)
        REFERENCES `instructors` (`instructor_id`) ON DELETE SET NULL;

CREATE TRIGGER increase_enrollment_count
    AFTER INSERT ON graduation_project.student_course
    FOR EACH ROW
BEGIN
    UPDATE graduation_project.courses
    SET student_enrolled = courses.student_enrolled + 1
    WHERE course_id = NEW.course_id;
END;



CREATE TRIGGER decrease_enrollment_count
    AFTER DELETE ON graduation_project.student_course
    FOR EACH ROW
BEGIN
    -- Ensure student_enrolled never goes below 0
    UPDATE graduation_project.courses
    SET student_enrolled = CASE
                               WHEN student_enrolled > 0 THEN student_enrolled - 1
                               ELSE 0
        END
    WHERE course_id = OLD.course_id;
    END;



# trigger to check if student passed the prerequisite course before registering the course

CREATE TRIGGER check_if_student_pass_prerequisite_course
    BEFORE INSERT ON graduation_project.student_course
    FOR EACH ROW
BEGIN
    DECLARE has_prerequisite_course BOOLEAN;
    DECLARE prerequisite_passed INT;
    DECLARE prerequisite_course_name VARCHAR(255);  -- Variable to hold the prerequisite course name
    DECLARE error_message VARCHAR(255);  -- Variable to hold the dynamic error message

    -- Check if the course has any prerequisites
    SET has_prerequisite_course = EXISTS (
        SELECT 1
        FROM courses c
                 JOIN courses c1 ON c.course_id = c1.prerequisites_course_id
        WHERE c1.course_id = NEW.course_id
    );

    -- If there are prerequisites, verify if student passed them
    IF has_prerequisite_course THEN
        -- Get the name of the prerequisite course
        SET prerequisite_course_name = (
            SELECT c.course_name
            FROM courses c
            WHERE c.course_id = (SELECT prerequisites_course_id FROM courses WHERE course_id = NEW.course_id LIMIT 1)
        );

        SET prerequisite_passed = (
            SELECT COUNT(*)
            FROM student_course sc
                     JOIN courses c ON sc.course_id = c.prerequisites_course_id
            WHERE sc.student_id = NEW.student_id
              AND sc.course_id IN (
                SELECT prerequisites_course_id
                FROM courses
                WHERE course_id = NEW.course_id
            )
              AND sc.degree >= 60  -- Assuming 60 is the passing grade
        );

        -- If no prerequisites passed, create error message and raise an error
        IF prerequisite_passed = 0 THEN
            SET error_message = CONCAT('Student has not passed the prerequisite course: ', prerequisite_course_name);
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
        END IF;
    END IF;
END;



####################

CREATE TRIGGER check_student_course_limit
    BEFORE INSERT ON student_course
    FOR EACH ROW
BEGIN
    DECLARE course_count INT;
    DECLARE max_courses INT;

    -- Determine the maximum allowed courses based on semester type
    IF NEW.semester_name = 'Summer' THEN
        SET max_courses = 2;  -- Summer semester allows only 2 courses
    ELSE
        SET max_courses = 6;  -- Fall and Spring allow up to 6 courses
    END IF;

    -- Count the number of courses the student is already enrolled in for the same semester and year
    SELECT COUNT(*)
    INTO course_count
    FROM student_course
    WHERE student_id = NEW.student_id
      AND semester_year_level = NEW.semester_year_level
      AND semester_name = NEW.semester_name;

    -- If the student has reached the limit, block the enrollment
    IF course_count >= max_courses THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Student cannot enroll in more than the allowed courses for this semester.';
    END IF;
END;

################################################################
alter table instructors
    add column bio text;

alter table instructors
    add column personal_image varchar(255);


alter table graduation_project.submission_request
    modify column academic_year enum('FIRST_YEAR','SECOND_YEAR','THIRD_YEAR','FOURTH_YEAR') DEFAULT 'FIRST_YEAR';

alter table graduation_project.students
    modify column academic_year enum('FIRST_YEAR','SECOND_YEAR','THIRD_YEAR','FOURTH_YEAR') DEFAULT 'FIRST_YEAR';


create table forgot_password
(
    fp_Id  int primary key auto_increment not null,
    otp int not null ,
    expiration_time DATETIME NOT NULL,
    user_id int,
    foreign key (user_id) REFERENCES users(id)

)

CREATE TABLE `tasks` (
    `task_id` INT PRIMARY KEY AUTO_INCREMENT,
    `course_id` INT NOT NULL,
    `instructor_id` INT NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `due_date` DATETIME NOT NULL,
    `max_grade` INT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE,
    FOREIGN KEY (`instructor_id`) REFERENCES `instructors`(`instructor_id`) ON DELETE CASCADE
);

CREATE TABLE `task_submissions` (
    `submission_id` INT PRIMARY KEY AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `student_id` INT NOT NULL,
    `file_path` VARCHAR(255) NOT NULL,
    `submission_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `grade` INT,
    `feedback` TEXT,
    `status` ENUM('SUBMITTED', 'GRADED') DEFAULT 'SUBMITTED',
    FOREIGN KEY (`task_id`) REFERENCES `tasks`(`task_id`) ON DELETE CASCADE,
    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE
);

CREATE TABLE quizzes (
                         quiz_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         description TEXT,
                         course_id INT NOT NULL,
                         duration INT NOT NULL COMMENT 'Duration in minutes',
                         total_degree INT NOT NULL COMMENT 'Total degree for the quiz',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

CREATE TABLE quiz_questions (
                                question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                question_text TEXT NOT NULL,
                                question_type ENUM('MCQ', 'TRUE_FALSE', 'SHORT_ANSWER') NOT NULL DEFAULT 'MCQ',

                                answer VARCHAR(255),
                                points INT NOT NULL,
                                quiz_id BIGINT,
                                FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);

CREATE TABLE question_options (
                                  option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  question_id BIGINT NOT NULL,
                                  option_text VARCHAR(255) NOT NULL,
                                  FOREIGN KEY (question_id) REFERENCES quiz_questions(question_id) ON DELETE CASCADE
);

