drop table if exists Student_information_management;

/*==============================================================*/
/* Table: Student_information_management                        */
/*==============================================================*/
create table Student_information_management
(
   StudentNumber        char(10) not null,
   password             varchar(255),
   PhoneNumber          char(11),
   ID                   char(18) not null,
   Name                 varchar(255),
   sex                  char(1),
   age                  int,
   Birthdate            date,
   primary key (StudentNumber, ID)
);
