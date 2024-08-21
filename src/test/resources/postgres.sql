CREATE TABLE users
(
    user_id    VARCHAR(255) PRIMARY KEY,
    login      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

INSERT INTO users (user_id, login, first_name, last_name)
VALUES ('1', 'login1', 'first_name1', 'last_name1'),
       ('2', 'login2', 'first_name2', 'last_name2'),
       ('3', 'login3', 'first_name3', 'last_name3'),
       ('4', 'login4', 'first_name4', 'last_name4'),
       ('5', 'login5', 'first_name5', 'last_name5'),
       ('6', 'login6', 'first_name6', 'last_name6');