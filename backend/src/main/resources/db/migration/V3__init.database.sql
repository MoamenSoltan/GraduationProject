CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
