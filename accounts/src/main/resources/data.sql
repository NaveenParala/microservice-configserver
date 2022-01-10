DROP table if exists customers;
DROP table if exists accounts;

create table customers ( customer_id int auto_increment primary key,
name varchar(100) not null,
email varchar(100) not null,
mobile_number varchar(20) not null,
create_date date default null
);
create table accounts (
customer_id int not null,
account_number int auto_increment primary key,
account_type varchar(100) not null,
branch_address varchar(200) not null,
create_date date default null
);

insert into customers (name,email,mobile_number,create_date)
values ('walgreens','wbapass@walgreen.com','9909087867',CURDATE());

insert into accounts (customer_id,account_number,account_type,branch_address,create_date)
values(1,186945634,'savings','123 main street wilmet road plml',CURDATE());

