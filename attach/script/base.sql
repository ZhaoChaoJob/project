-- =========== 建表语句 ==========

-- 用户信息表
-- drop table if exists t_sys_user;
create table t_sys_user (
  `uid` 					numeric(20) 	not null 	comment '用户id' 		,
  `create_name_id` 			numeric(20) 				comment '创建人id' 		,
  `create_time` 			numeric(14) 				comment '创建时间' 		,
  `email` 					varchar(50) 				comment '邮箱|登录帐号' ,
  `last_login_time` 		numeric(14) 				comment '最后登录时间' 	,
  `last_update_name_id` 	numeric(20) 				comment '最后修改人id' 	,
  `last_update_time`		numeric(14) 				comment '最后修改时间' 	,
  `nickname` 				varchar(120) 				comment '用户昵称' 		,
  `password` 				varchar(120) 				comment '密码' 			,
  `status` 					numeric(4) 					comment '用户状态,0:创建未认证, 1:正常状态,2：用户被锁定'
) engine=innodb comment '用户信息表' ;
alter table t_sys_user add primary key (uid) ;
create index ix_sys_user_email on t_sys_user (email) ;

-- 用户角色表
-- drop table if exists t_sys_role;
create table t_sys_role (
  `role_id` 		numeric(20) 	not null 	comment '角色id' 					,
  `description` 	varchar(200)  				comment '角色描述,UI界面显示使用' 	,
  `role_name` 		varchar(100)  				comment '角色名称'
) engine=innodb comment '用户角色表' ;
alter table t_sys_role add primary key (role_id) ;

-- 用户权限表
-- drop table if exists t_sys_permission;
create table t_sys_permission (
  `permission_id` 	numeric(20) not null 	comment '权限id' 		,
  `available` 		numeric(1) 				comment '' 				,
  `name` 			varchar(255)  			comment '名称' 			,
  `parent_id` 		numeric(20) 			comment '父编号' 		,
  `parent_ids` 		varchar(255)  			comment '父编号列表' 	,
  `permission_str` 	varchar(255)  			comment '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view' ,
  `resource_type` 	enum('menu','button')  	comment '资源类型，[menu|button]' ,
  `url` 				varchar(255)   			comment '资源路径'
) engine=innodb comment '用户权限表' ;
alter table t_sys_permission add primary key (permission_id) ;

-- 角色&权限关联表
-- drop table if exists t_sys_role_permission;
create table t_sys_role_permission (
  `role_id` 			numeric(20) not null 	comment '角色ID' ,
  `permission_id` 		numeric(20) not null	comment '权限ID'
) engine=innodb comment '角色&权限关联表' ;
alter table t_sys_role_permission add foreign key(role_id) references t_sys_role(role_id);
alter table t_sys_role_permission add foreign key(permission_id) references t_sys_permission(permission_id);


-- 用户&角色关联表
-- drop table if exists t_sys_user_role;
create table t_sys_user_role (
  `uid` 				numeric(20) not null	comment '用户ID' ,
  `role_id` 			numeric(20) not null	comment '角色ID'
) engine=innodb comment '用户&角色关联表' ;
alter table t_sys_user_role add foreign key(role_id) references t_sys_user(uid);
alter table t_sys_user_role add foreign key(role_id) references t_sys_role(role_id);

-- 用户Token表，主要用户Api调用
-- drop table if exists t_sys_token;
create table t_sys_token (
  `token_id` 			numeric(20) not null	comment '令牌ID' 	,
  `user_id` 			numeric(20) 			comment '用户ID' 	,
  `user_code`			varchar(20)  			comment '账号' 		,
  `password` 			varchar(32)  			comment '密码' 		,
  `insert_time` 		numeric(14) 			comment '插入时间' 	,
  `invalid_time` 		numeric(14) 			comment '失效时间' 	,
  `status` 				numeric(1) 				comment '令牌状态'
) engine=innodb comment '用户Token表，主要用户Api调用' ;
alter table t_sys_token add primary key (token_id) ;

-- 测试用表


