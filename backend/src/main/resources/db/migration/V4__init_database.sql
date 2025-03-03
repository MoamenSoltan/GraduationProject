use graduation_project;

alter table graduation_project.submission_request add
    column gender ENUM('MALE', 'FEMALE');

# use graduation_project;
insert into graduation_project.department values (1,'general',1,now())
        ,(2,'CS',1,now()),
                              (3,'IT',1,now()),
                              (4,'IS',1,now());