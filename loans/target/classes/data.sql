drop table if exists loans;

create table loans(
loan_number int not null auto_increment,
customer_id int not null,
start_date date not null,
loan_type varchar(100) not null,
total_loan int not null,
amount_paid int not null,
outstanding_amount int not null,
create_date date default null,
primary key(loan_number)
);

insert into loans(customer_id,start_date,loan_type,total_loan,amount_paid,outstanding_amount,create_date)
values(1,'2020-10-13','home',250000,50000,200000,'2020-10-13'),
(1,'2020-10-06','vehicle',150000,50000,100000,'2020-10-06'),
(1,'2020-10-14','personal',300000,50000,250000,'2020-10-14'),
(1,'2020-10-16','home',500000,100000,400000,'2020-10-16');
