use graduation_project;
create table roles(
    id int primary key auto_increment not null,
    role_name enum('ADMIN','STUDENT','INSTRUCTOR') default 'STUDENT'
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
CREATE TABLE `submission_request` (
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
   `admission_status` ENUM('Pending', 'Accepted', 'Rejected') DEFAULT 'Pending',
   `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   `user_id` INTEGER NULL,
   FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);


create table department
(
    department_id int primary key auto_increment,
    department_name enum('CS','IT','IS','general') default 'general',
    head_of_department_id int ,
    created_at timestamp ,
    foreign key (head_of_department_id) references users(id)

);

create table students(
    student_id int primary key auto_increment,
    academic_year int,
    gpa double,
    department_id int,
    fees_status enum('paid','pending'),
    created_at timestamp ,
    foreign key (department_id) references department(department_id),
    foreign key (student_id) references users(id)

);

CREATE table instructors(
    instructor_id int primary key auto_increment,
    department_id int,
    created_at timestamp ,
    foreign key (department_id) references department(department_id),
    foreign key (instructor_id) references users(id)
);

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
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `student_id` INTEGER NOT NULL,
    `course_id` INTEGER NOT NULL,
    `semester_id` INTEGER NOT NULL,
    `grade` DOUBLE,
    `enrollment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE,
    FOREIGN KEY (`semester_id`) REFERENCES `semester`(`semester_id`) ON DELETE CASCADE
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
