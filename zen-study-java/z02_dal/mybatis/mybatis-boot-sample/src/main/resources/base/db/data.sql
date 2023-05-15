insert into user (name) values ('li1');
insert into user (name) values ('li2');
insert into user (name) values ('li3');
insert into user (name) values ('li4');


insert into consumer (id, user_name) values (1,'zhang3');
insert into consumer (id, user_name) values (2,'li4');

insert into `order` (id, order_time, amount, user_id)
values (1,now(),23.4,1);
insert into `order` (id, order_time, amount, user_id)
values (2,null,38.9,1);
insert into `order` (id, order_time, amount, user_id)
values (3,now(),34.7,2);



