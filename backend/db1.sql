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
                            `semester_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
                            `year_level` INTEGER NOT NULL,
                            `semester_name` ENUM('Fall', 'Spring', 'Summer') NOT NULL,
                            `start_date` DATE NOT NULL,
                            `end_date` DATE NOT NULL,
                            `is_active` BOOLEAN DEFAULT FALSE,
                            `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
create table courses(
                        course_id int primary key auto_increment,
                        course_name varchar(255) not null,
                        course_code varchar(255) not null,
                        credit int ,
                        department_id int,
                        semester_id int,
                        instructor_id int,
                        prerequisites_course_id int,
                        `course_description` TEXT,
                        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        foreign key (department_id) references department(department_id),
                        foreign key (semester_id) references semester(semester_id),
                        foreign key (instructor_id) references instructors(instructor_id),
                        foreign key (prerequisites_course_id) references courses(course_id)
);

CREATE TABLE `student_course` (

                                  `student_id` INTEGER NOT NULL,
                                  `course_id` INTEGER NOT NULL,
                                  `semester_id` INTEGER NOT NULL,
                                  `grade` DOUBLE,
                                  `enrollment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                  primary key (student_id,course_id,semester_id),
                                  FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE,
                                  FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE,
                                  FOREIGN KEY (`semester_id`) REFERENCES `semester`(`semester_id`) ON DELETE CASCADE
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