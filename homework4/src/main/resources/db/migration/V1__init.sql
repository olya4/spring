create table if not exists users (
    id         	bigserial primary key,
    username   	varchar(255) not null,
    password   	varchar(255) not null,
    email      	varchar(255) unique,
    created_at 	timestamp default current_timestamp,
    updated_at 	timestamp default current_timestamp
);

create table if not exists roles (
    id         	bigserial primary key,
    name       	varchar(255) not null,
    created_at 	timestamp default current_timestamp,
    updated_at 	timestamp default current_timestamp
);

create table if not exists users_roles (
    user_id 	bigint not null references users (id),
    role_id 	bigint not null references roles (id),
    primary key (user_id, role_id),
    created_at 	timestamp default current_timestamp,
    updated_at 	timestamp default current_timestamp
);

create table if not exists products (
	id 			bigserial primary key,
	title 		varchar(255) not null,
	price 		int not null,
	created_at 	timestamp default current_timestamp,
    updated_at 	timestamp default current_timestamp
);

create table if not exists orders (
    id              bigserial primary key,
    user_id         bigint not null references users (id),
    total_price     int not null,
    address     	varchar(255) not null,
    telephone       varchar(255) not null,
    created_at 		timestamp default current_timestamp,
    updated_at 		timestamp default current_timestamp
);

create table if not exists order_items (
    id               bigserial primary key,
    order_id         bigint not null references orders (id),
    product_id       bigint not null references products (id),
    quantity         int not null,
    price_per_piece  int not null,
    price            int not null,
    created_at 		 timestamp default current_timestamp,
    updated_at       timestamp default current_timestamp
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

insert into products (title, price)
values
('Orange', 100),
('Raspberry', 55),
('Banana', 40),
('Grapefruit', 10),
('Peach', 75),
('Apricot', 25),
('Pear', 60),
('Apple', 15),
('Pineapple', 35),
('Watermelon', 80),
('Melon', 20),
('Mandarin', 70),
('Plum', 45),
('Papay', 30),
('Blueberry', 85),
('Cherry', 50),
('Pomelo', 65),
('Strawberry', 95),
('Quince', 5),
('Grape', 90);