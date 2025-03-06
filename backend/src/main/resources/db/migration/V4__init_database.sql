use graduation_project;

-- alter table graduation_project.submission_request add
--     column gender ENUM('MALE', 'FEMALE');

# use graduation_project;
insert into graduation_project.department values (1,'general',null,now())
        ,(2,'CS',null,now()),
                              (3,'IT',null,now()),
                              (4,'IS',null,now());

ALTER TABLE graduation_project.courses
    ADD COLUMN max_students INT DEFAULT 200,
ADD COLUMN year ENUM('FIRST_YEAR', 'SECOND_YEAR', 'THIRD_YEAR', 'FOURTH_YEAR'),
ADD COLUMN type ENUM('REQUIRED', 'OPTIONAL'),
ADD COLUMN schedule VARCHAR(255),
ADD COLUMN student_enrolled INT DEFAULT 0;