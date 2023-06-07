insert into user (name) values ('li1');
insert into user (name) values ('li2');
insert into user (name) values ('li3');
insert into user (name) values ('li4');

-- 场景1: 普通json的key
insert into tab_json values (1,
    '{"k1": 12, "k2": 34, "k3": {"L1": {"M1":1,"M2":2}, "L2": "value2"}}');
insert into tab_json VALUES (2,
     '{"success": true,"code": "0","message": "",
     "data": {"name": "jerry","age": "18","sex": "男"}}');

-- 场景2: 特殊字符使用 ""
insert into tab_json VALUES (3, '{"1": true,"l-l": "0"}');
-- 场景3: json数组查询
insert into tab_json VALUES (4,
 '{"success": true
 "data": [{"name": "jerry","age": "18"},{"name": "jack","age": "20"}]}');
insert into tab_json VALUES (5, '{"success": true,"data": [1,2,3,4,5]}');

