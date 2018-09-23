drop database if exists groupred;
create database groupred;
use groupred;

create table team
(id bigint auto_increment,
name varchar(255) not null,
active boolean not null default false,
max_users int not null default 10,
primary key(id));

#om man tar bort team så sätts den till null
create table user
(id bigint auto_increment,
first_name varchar(255) not null,
last_name varchar(255) not null,
user_name varchar(255) not null,
active boolean not null default false,
user_number bigint unique not null,
team_id bigint,
foreign key(team_id) references team(id) on delete set null on update cascade,
primary key(id));

#kan status vara null?
create table task
(id bigint auto_increment,
title varchar(255) not null,
description varchar(255) not null,
status varchar(255) not null default false,
user_id bigint,
foreign key(user_id) references user(id) on delete set null on update cascade,
primary key(id));

#om man tar bort en task så tas issuen bort
create table issue
(id bigint auto_increment,
task_id bigint not null,
title varchar(255) not null,
description varchar(255) not null,
foreign key(task_id) references task(id) on delete cascade on update cascade,
primary key(id));