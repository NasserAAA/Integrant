create table IF NOT EXISTS users(
`user_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(60) not null,
`email` varchar(60) not null unique,
`password` varchar(200) not null,
`enabled` boolean not null default false
);
create table IF NOT EXISTS roles(
`role_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(60) not null unique
);
create table IF NOT EXISTS privilege(
`privilege_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(60) not null unique
);
create table IF NOT EXISTS users_roles(
`user_id` int,
`role_id` int,
primary key (`user_id`,`role_id`),
foreign key (`user_id`) references users(`user_id`) on delete cascade on update cascade,
foreign key (`role_id`) references roles(`role_id`) on delete cascade on update cascade
);
create table IF NOT EXISTS roles_privileges(
`role_id` int,
`privilege_id` int,
primary key (`privilege_id`,`role_id`),
foreign key (`role_id`) references roles(`role_id`) on delete cascade on update cascade,
foreign key (`privilege_id`) references privilege(`privilege_id`) on delete cascade on update cascade
);
create table IF NOT EXISTS confirmationtoken(
`token_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`confirmation_token` varchar(200),
`createdDate` datetime,
`expiryDate` datetime,
`user_id` int,
foreign key(`user_id`) references users(`user_id`) on delete cascade on update cascade
);
create table IF NOT EXISTS tweets(
`tweet_id` varchar(255) NOT NULL PRIMARY KEY,
`tweet_text` varchar(550) not null,
`sentiment_score` double not null,
`created_at` datetime not null
);
create table IF NOT EXISTS authors(
`author_id` int NOT NULL PRIMARY KEY,
FOREIGN KEY(`author_id`) REFERENCES users(`user_id`)  on delete cascade on update cascade

);
create table IF NOT EXISTS books(
`book_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`title` varchar(60) NOT NULL,
`author` int NOT NULL,
`uniquetitle` varchar(60) NOT NULL UNIQUE,
`stock` int NOT NULL default 0 ,
`instock` boolean NOT NULL default false,
foreign key (`author`) references authors(`author_id`) on delete cascade on update cascade
);
create table IF NOT EXISTS tags(
`tag_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(60) NOT NULL UNIQUE
);
create table IF NOT EXISTS books_tags(
`book_id` int,
`tag_id` int,
primary key (`book_id`,`tag_id`),
foreign key (`book_id`) references books(`book_id`) on delete cascade on update cascade,
foreign key (`tag_id`) references tags(`tag_id`) on delete cascade on update cascade
);
