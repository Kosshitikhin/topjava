DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (meal_id, user_id, date_time, description, calories)
VALUES (1, 100000, '2020-12-20 12:00:00' , 'Обед', 550),
       (2, 100001,  '2020-12-21 09:00:00', 'Завтрак', 1000);
