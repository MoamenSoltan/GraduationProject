# create schema graduation_project;
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

create table tasks(
    task_id int primary key auto_increment,
    course_id int ,
    instructor_id int,
    task_name varchar(255) not null,
    task_description text,
    deadline date,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,

    foreign key (course_id) references courses(course_id),
    foreign key (instructor_id) references instructors(instructor_id)
);

create table task_submissions (
    submission_id int primary key auto_increment,
    task_id int,
    student_id int,
    submission_date timestamp DEFAULT CURRENT_TIMESTAMP,
    submission_file text,
    grade double,

    foreign key (task_id) references tasks(task_id),
    foreign key (student_id) references students(student_id)
);

CREATE TABLE `payments` (
    `payment_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `student_id` INTEGER NOT NULL,
    `amount` DOUBLE NOT NULL,
    `payment_date` timestamp DEFAULT CURRENT_TIMESTAMP,
    `status` ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    `payment_method` ENUM('Credit Card', 'Bank Transfer', 'Cash', 'Other'),
    `transaction_id` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE
);

CREATE TABLE `feedback` (
    `feedback_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `student_id` INTEGER NOT NULL,
    `course_id` INTEGER NOT NULL,
    `rating` DOUBLE CHECK(`rating` >= 0 AND `rating` <= 5),
    `comment` TEXT,
    `submitted_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE
);
