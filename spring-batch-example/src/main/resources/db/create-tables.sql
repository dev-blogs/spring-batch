create table products (
	id varchar(10),
	name varchar(255),
	description varchar(255),
	price decimal(19,2)
);

create table reports (
	id int primary key auto_increment not null,
	exit_status_code varchar(25),
	description varchar(50)
);
