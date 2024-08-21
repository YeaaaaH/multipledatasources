CREATE TABLE user_table
(
    ldap_login VARCHAR(255) PRIMARY KEY,
    name       VARCHAR(255),
    surname    VARCHAR(255)
);

INSERT INTO user_table (ldap_login, name, surname)
VALUES ('login1', 'name1', 'surname1'),
       ('login2', 'name2', 'surname2'),
       ('login3', 'name3', 'surname3'),
       ('login4', 'name4', 'surname4'),
       ('login5', 'name5', 'surname5'),
       ('login6', 'name6', 'surname6')