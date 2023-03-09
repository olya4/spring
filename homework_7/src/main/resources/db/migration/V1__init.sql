create table if not exists students (id bigserial primary key, name varchar(255) unique, age int not null);

insert into students (name, age)
values
('George', 18),
('Richard', 17),
('Emma', 20),
('James', 19),
('Thomas', 19),
('Mary', 21),
('Sandra', 17),
('Elizabeth', 18),
('Robert', 20),
('John', 19);