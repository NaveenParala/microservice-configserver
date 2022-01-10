drop table if exists cards;

create table cards(
card_id int not null auto_increment,
customer_id int not null,
card_number varchar(100) not null,
card_type varchar(100) not null,
total_limit int not null,
amount_used int not null,
available_amount int not null,
create_date date default null,
primary key(card_id)
);

insert into cards(customer_id,card_number,card_type,total_limit,amount_used,available_amount,create_date)
values(1,'456545678933','credit',250000,50000,200000,CURDATE()),
(1,'34235456576722','credit',150000,50000,100000,CURDATE()),
(1,'454455667711','credit',300000,50000,250000,CURDATE())

