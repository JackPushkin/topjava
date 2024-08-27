DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2024-01-08 04:05:06', 'description', 1500, 100000),
       ('2021-01-08 10:05:06', 'description', 1200, 100000),
       ('2023-01-08 12:05:06', 'description', 1000, 100001),
       ('2022-01-08 05:05:06', 'description', 500, 100002);
