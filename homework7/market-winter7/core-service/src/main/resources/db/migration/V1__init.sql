create table categories (
    id         bigserial primary key,
    title       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories (title)
values ('fruits'),
       ('vegetables'),
       ('berries'),
       ('others');

create table if not exists products (
id bigserial primary key,
title varchar(255),
category_id bigint references categories(id),
price int,
created_at timestamp default current_timestamp,
updated_at timestamp default current_timestamp
);

insert into products (title, price, category_id )
values
('Orange', 100, 1),
('Raspberry', 55, 3),
('Banana', 40, 1),
('Carrot', 10, 2),
('Peach', 75, 1),
('Apricot', 25, 1),
('Tomato', 60, 2),
('Apple', 15, 1),
('Pineapple', 35, 1),
('Watermelon', 80, 3),
('Cabbage', 20, 2),
('Mandarin', 70, 1),
('Plum', 45, 1),
('Potato', 30, 2),
('Blueberry', 85, 3),
('Cherry', 50, 3),
('Cucumber', 65, 2),
('Strawberry', 95, 3),
('Onion', 5, 2),
('Grape', 90, 3);

create table orders
(
    id              bigserial primary key,
    username        varchar(255) not null,
    total_price     int    not null,
    address         varchar(255),
    phone           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

