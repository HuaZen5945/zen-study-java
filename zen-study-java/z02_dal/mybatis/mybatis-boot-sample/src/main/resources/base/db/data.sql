insert into user (name) values ('li1');
insert into user (name) values ('li2');
insert into user (name) values ('li3');
insert into user (name) values ('li4');

-- 多对多查询
insert into consumer (id, user_name) values (1,'zhang3');
insert into consumer (id, user_name) values (2,'li4');

insert into `order` (id, order_time, amount, user_id)
values (1,now(),23.4,1);
insert into `order` (id, order_time, amount, user_id)
values (2,null,38.9,1);
insert into `order` (id, order_time, amount, user_id)
values (3,now(),34.7,2);

--  去重数据
insert into user_map (id, name, sex, type)
values (1,"li1",'male',"aaa");
insert into user_map (id, name, sex, type)
values (1,"li2",'male',"aaa");
insert into user_map (id, name, sex, type)
values (1,"w1",'female',"aaa");
insert into user_map (id, name, sex, type)
values (1,"w2",'female',"aaa");


-- 场景1: 普通json的key
insert into tab_json values (null,
    '{"k1": 12, "k2": 34, "k3": {"L1": {"M1":1,"M2":2}, "L2": "value2"}}');
insert into tab_json VALUES (null,
     '{"success": true,"code": "0","message": "",
     "data": {"name": "jerry","age": "18","sex": "男"}}');

-- 场景2: 特殊字符使用 ""
insert into tab_json VALUES (null, '{"1": true,"l-l": "0"}');
-- 场景3: json数组查询
insert into tab_json VALUES (null,
 '{"success": true
 "data": [{"name": "jerry","age": "18"},{"name": "jack","age": "20"}]}');
insert into tab_json VALUES (null, '{"success": true,"data": [1,2,3,4,5]}');

