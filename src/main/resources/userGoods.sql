create table UserGoods(
   id number(4),
   userName varchar2(20),
   goodName varchar2(20),
   goodPrice number(10),
   goodInfo varchar2(200)
);

insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (1,'Lili','苹果',5,'苹果5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (2,'Lili','梨子',5,'梨子5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (3,'Lili','香蕉',5,'香蕉5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (4,'Amy','苹果',5,'苹果5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (5,'Amy','梨子',5,'梨子5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (6,'Amy','香蕉',5,'香蕉5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (7,'John','苹果',5,'苹果5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (8,'John','梨子',5,'梨子5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (9,'John','香蕉',5,'香蕉5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (10,'Lee','苹果',5,'苹果5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (12,'Lee','梨子',5,'梨子5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (11,'Lee','香蕉',5,'香蕉5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (13,'James','苹果',5,'苹果5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (14,'James','梨子',5,'梨子5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (15,'James','香蕉',5,'香蕉5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (16,'Blike','苹果',5,'苹果5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (17,'Blike','梨子',5,'梨子5块一斤');
insert into UserGoods(id,userName,Goodname,Goodprice,Goodinfo) values (18,'Blike','香蕉',5,'香蕉5块一斤');

select * from UserGoods;
