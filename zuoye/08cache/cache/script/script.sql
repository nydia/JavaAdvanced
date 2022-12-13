create schema test;

drop table if EXISTS user;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert into user(name, age) values('test1', 1);
insert into user(name, age) values('test2', 2);
insert into user(name, age) values('test3', 3);
insert into user(name, age) values('test4', 4);
insert into user(name, age) values('test5', 5);
insert into user(name, age) values('test6', 6);


drop table if EXISTS td_order;

CREATE TABLE `td_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert into td_order(order_no) values('no1');
insert into td_order(order_no) values('no2');
insert into td_order(order_no) values('no3');
insert into td_order(order_no) values('no4');
insert into td_order(order_no) values('no5');



drop table if EXISTS author;

CREATE TABLE `author` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `genre` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert into author(id,name, age, genre) values(1,'test1', 1, "1");
insert into author(id,name, age, genre) values(2,'test2', 2, "1");
insert into author(id,name, age, genre) values(3,'test3', 3, "1");
insert into author(id,name, age, genre) values(4,'test4', 4, "1");
insert into author(id,name, age, genre) values(5,'test5', 5, "1");
insert into author(id,name, age, genre) values(6,'test6', 6, "1");