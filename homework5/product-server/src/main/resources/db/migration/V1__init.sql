create table if not exists products (
id bigserial primary key,
title varchar(255),
price int,
created_at timestamp default current_timestamp,
updated_at timestamp default current_timestamp
);

insert into products (title, price)
values
('Orange', 100),
('Raspberry', 55),
('Banana', 40),
('Carrot', 10);
