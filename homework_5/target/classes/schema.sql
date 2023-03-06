drop table students IF EXISTS;

create table IF NOT EXISTS students (
id bigserial primary key,
name VARCHAR(255) unique,
mark int);

insert into students (name, mark) values
('George', 55),
('Richard', 90),
('Emma', 80),
('James', 70),
('Thomas', 95),
('Mary', 50),
('Sandra', 60),
('Elizabeth', 40),
('Robert', 35),
('John', 65);
