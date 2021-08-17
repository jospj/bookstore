DROP TABLE IF EXISTS BOOK;
create table BOOK
(
   id int(10),
   isbn varchar2 (20) not null,
   name varchar2 (255) not null,
   description varchar2 (500) not null,
   author varchar2 (255) not null,
   book_type varchar2 (255) not null,
   price numeric (8,2)  not null,
   primary key(id)
);