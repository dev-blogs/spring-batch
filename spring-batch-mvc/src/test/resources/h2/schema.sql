CREATE TABLE product_import (
	import_id	VARCHAR2(15),
	job_instance_id	INT,
	content		VARCHAR2(4000),
	creation_date	DATE
);

create table products (
	id varchar(10),
	name varchar(255),
	description varchar(255),
	price decimal(19,2)
);
