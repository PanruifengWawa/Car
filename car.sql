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
alter table t_user add column career_count int not null default 0;

alter table t_user add column school varchar(200);
alter table t_user add column contact varchar(200);
alter table t_user add column id_number varchar(200);
alter table t_user add column photo varchar(200);
alter table t_user add column mentor varchar(200);
alter table t_user add column degree_type varchar(200);

INSERT INTO `car`.`t_user` (`id`, `user_name`, `password`, `name`, `state`, `email`, `register_date`, `school_year`, `type`) VALUES ('3', 'admin', '2818be0fece8b901a95b4ceeb639cbdf', 'admin', '1', '', '2017-02-28 19:04:35', '', '0');

DROP TABLE IF EXISTS t_file;
create table t_file(
id serial primary key,
title varchar(256),
owner varchar(200),
content text,
date datetime,
url varchar(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into t_file(id,title) values(1,"无文件");

DROP TABLE IF EXISTS t_material;
create table t_material(
id serial primary key,
file_id bigint(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_career_plan;
create table t_career_plan(
id serial primary key,
user_id bigint(11),
title varchar(256),
content text,
file_id bigint(11),
state int,
response text,
plandate date,
comdate date,
resdate date,
user_name varchar(50),
name varchar(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_project;
create table t_project(
id serial primary key,
name varchar(256),
regdate date,
plan_count int
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS r_project_member;
create table r_project_member(
id serial primary key,
project_id bigint(11),
user_id bigint(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE r_project_member  
ADD UNIQUE KEY(project_id, user_id); 

DROP TABLE IF EXISTS t_project_file;
create table t_project_file(
id serial primary key,
project_id bigint(11),
file_id bigint(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_project_plan;
create table t_project_plan(
id serial primary key,
project_id bigint(11),
title varchar(256),
file_id bigint(11),
content text,
state int,
response text,
plandate date,
comdate date,
resdate date
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop trigger if exists careerCountTrigger;
delimiter || 
create trigger careerCountTrigger before insert on t_career_plan for each row   
begin 
	declare userName varchar(50);
	declare name varchar(200);
	update t_user u set u.career_count = 1 where u.id = new.user_id;
	select u.user_name into userName from t_user u where u.id = new.user_id;
    select u.name into name from t_user u where u.id = new.user_id;
	set new.name := name;
	set new.user_name := userName;
end||
delimiter ;



drop trigger if exists projectCountTrigger;
delimiter || 
create trigger projectCountTrigger after insert on t_project_plan for each row   
begin 
	update t_project p set p.plan_count = 1 where p.id = new.project_id;
end||
delimiter ;