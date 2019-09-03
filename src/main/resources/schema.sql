create table IF NOT EXISTS authors(
`author_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(60) NOT NULL,
`email` varchar(60) NOT NULL UNIQUE
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
