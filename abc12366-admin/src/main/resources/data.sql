-- 初始化数据
# insert into user (username, password, phone, enabled) values ('test01', 'test01', '13700137001', '1');
insert into sys_dict(id, dictId, dictName, fieldKey, fieldValue, status, createTime, createUser, lastUpdate, lastUser) values('8cc2385f-f82d-4301-b20f-fca1aac3bc10', 'test01', 'test01', 'test01', 'test01', '1', '2017-04-27 09:52:04', 'test01', '2017-04-27 09:52:04', 'test01' );
insert into sys_dict(id, dictId, dictName, fieldKey, fieldValue, status, createTime, createUser, lastUpdate, lastUser) values('8cc2385f-f82d-4301-b20f-fca1aac3bc11', '1', '1', '1', '1', '1', '2017-04-27 09:52:04', '1', '2017-04-27 09:52:04', '1' );

insert into sys_menu(menuId, menuName, menuUrl, parentId, perms, type, icon, sort, status, remark) values('8cc2385f-f82d-4301-b20f-fca1aac3bca8','test01','test01',NULL ,'test01','1','test01',1 ,true, 'test01');
insert into sys_menu(menuId, menuName, menuUrl, parentId, perms, type, icon, sort, status, remark) values('8cc2385f-f82d-4301-b20f-fca1aac3bca9','test02','test02','8cc2385f-f82d-4301-b20f-fca1aac3bca8','test01','2','test01',2 ,true, 'test01');
insert into sys_menu(menuId, menuName, menuUrl, parentId, perms, type, icon, sort, status, remark) values('8cc2385f-f82d-4301-b20f-fca1aac3bc10','test03','test03','8cc2385f-f82d-4301-b20f-fca1aac3bca8','test03','2','test03',2 ,true, 'test03');
insert into sys_menu(menuId, menuName, menuUrl, parentId, perms, type, icon, sort, status, remark) values('8cc2385f-f82d-4301-b20f-fca1aac3bc11','test04','test04','8cc2385f-f82d-4301-b20f-fca1aac3bca9','test04','2','test04',2 ,true, 'test04');
insert into sys_menu(menuId, menuName, menuUrl, parentId, perms, type, icon, sort, status, remark) values('8cc2385f-f82d-4301-b20f-fca1aac3bc12','test04','test04','8cc2385f-f82d-4301-b20f-fca1aac3bc10','test04','2','test04',2 ,true, 'test04');
insert into sys_menu(menuId, menuName, menuUrl, parentId, perms, type, icon, sort, status, remark) values('8cc2385f-f82d-4301-b20f-fca1aac3bc13','test04','test04','8cc2385f-f82d-4301-b20f-fca1aac3bc10','test04','2','test04',2 ,true, 'test04');
