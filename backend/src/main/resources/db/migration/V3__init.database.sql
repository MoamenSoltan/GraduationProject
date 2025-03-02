use graduation_project;
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO users (first_name, last_name, gender, email, password)
VALUES ('Admin', 'User', 'MALE', 'admin@example.com', '$2a$10$rp/QTc5rfYAUCPanwoyXE.hXrzauN7qa4M7xGBM/VucPj3blvUJNa');

insert into user_roles values (1,1);

