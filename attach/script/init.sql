-- 新增一个超级用户
-- 系统用户区间1000-2000
insert into t_sys_user(user_id,create_name_id,create_time,email,last_login_time,last_update_name_id,last_update_time,nickname,password,status)
values ( 1001,null,20120828113719,'albg_boy@163.com',20120903160853,20120903160853,null,'admin','123',1);

-- 新增一个角色
-- 系统层 1000-2000

insert into t_sys_role(role_id,description,role_name)
values (1001,'超级管理员','超级管理员');

-- 新增用户权限
-- 系统层级预留 1000-2000
-- 菜单、按钮、api、
insert into t_sys_permission(permission_id,available,name,parent_id,parent_ids,permission_str,resource_type,url)
values(1001,1,'测试权限',null,null,'test:*','菜单','/test/*');

-- 新增角色关联权限
insert into t_sys_role_permission(role_id,permission_id)
values(1001,1001);

-- 新增一个用户组
insert into t_sys_user_group(group_id,group_name)
values(1001,'超级用户组');

insert into t_sys_user_group_role(group_id,role_id)
values(1001,1001);

insert into t_sys_user_user_group(user_id,group_id)
values(1001,1001);


insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('01','一级菜单1',1,null,'一级菜单1的描述',1001,'icon01');
insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('011','二级菜单子菜单1',2,'01','二级菜单子菜单1',1001,'icon01');
insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('012','二级菜单子菜单2',2,'01','二级菜单子菜单2',1001,'icon01');
insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('013','二级菜单子菜单3',2,'01','二级菜单子菜单3',1001,'icon01');
insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('02','一级菜单2',1,null,'一级菜单2的描述',1001,'icon01');
insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('021','二级菜单子菜单21',2,'02','二级菜单子菜单21',1001,'icon01');
insert into t_sys_menu(menu_id,title,level,parent_id,`desc`,permission_id,icon)
values('0211','三级菜单子菜单021',3,'021','三级菜单子菜单021',1001,'icon01');

select m.*,p.url
from t_sys_user_user_group UP
LEFT JOIN t_sys_user_group g
on up.user_id=1001 and UP.group_id=g.group_id
LEFT JOIN t_sys_user_group_role gr
on g.group_id=gr.group_id
LEFT JOIN t_sys_role r
on GR.role_id=r.role_id
LEFT JOIN t_sys_role_permission rp
on r.role_id=rp.role_id
LEFT JOIN t_sys_permission p
on RP.permission_id=p.permission_id
LEFT JOIN t_sys_menu m
on p.permission_id=m.permission_id

