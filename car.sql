create database car;

use car;
DROP TABLE IF EXISTS t_user;
create table t_user(
id serial primary key,
user_name varchar(50) unique,
password varchar(200) not null,
name varchar(200),
state int,
email varchar(50),
register_date datetime,
school_year varchar(10),
type int
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `car`.`t_user` (`id`, `user_name`, `password`, `name`, `state`, `email`, `register_date`, `school_year`, `type`) VALUES ('3', 'admin', '2818be0fece8b901a95b4ceeb639cbdf', 'admin', '1', '', '2017-02-28 19:04:35', '', '0');

