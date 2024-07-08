drop database if exists user_data;
create database user_data;
drop table if exists user_data;

/*==============================================================*/
/* Table: user_data                                             */
/*==============================================================*/
create table user_data
(
   name                 varchar(255),
   Pnumber              varchar(255),
   ID                   char(18) not null,
   bkID                 varchar(19) not null,
   balance              double,
   password             char(6),
   primary key (ID, bkID)
);

insert into user_data(name,Pnumber,ID,bkID,balance,password)values('jack','13539645372','440513200410053454','123456789058',1000,'123456');
insert into user_data(name,Pnumber,ID,bkID,balance,password)values('Mary','15975385258','440513200405143333','987654321584',15000,'123456');
insert into user_data(name,Pnumber,ID,bkID,balance,password)values('ccc','16575924862','440513200308094444','9856472642851',20000,'123456');
update user_data set balance = 50000 where name = 'jack';