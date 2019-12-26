CREATE DATABASE IF NOT EXISTS demo DEFAULT character set utf8mb4 collate utf8mb4_unicode_ci;
use demo;

create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);


drop table if exists sys_department;

drop table if exists sys_dict;

drop table if exists sys_login_log;

drop table if exists sys_permission;

drop table if exists sys_role;

drop table if exists sys_role_permission;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: sys_department                                        */
/*==============================================================*/
create table sys_department
(
    id                   varchar(64) not null,
    pid                  varchar(64) not null default '0' comment '上级部门ID',
    name                 varchar(64) not null comment '名称',
    manager_id           varchar(64) comment '管理员ID',
    level                tinyint not null default 0 comment '级别',
    del_flag             tinyint not null default 0 comment '是否删除（0--否；1--是）',
    description          varchar(255) comment '释义',
    status               tinyint not null default 0 comment '状态（0--正常；1--停用）',
    create_time          timestamp not null default CURRENT_TIMESTAMP comment '注册时间',
    update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    create_user_id       varchar(64) comment '填表人ID',
    update_user_id       varchar(64) comment '最后更新人ID',
    primary key (id)
);

alter table sys_department comment '部门表';

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
    id                   varchar(64) not null comment '主键',
    table_field          varchar(32) not null comment '字段全名（表名_字段名）',
    dict_code            varchar(16) not null comment '字典代码',
    dict_name            varchar(16) not null comment '代码名称',
    dict_desc            varchar(64) comment '代码描述',
    order_num            tinyint not null default 1 comment '排序优先级',
    create_user_id       varchar(64) comment '创建人ID',
    create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
    primary key (id),
    unique key uk_sys_dict_field_code (table_field, dict_code)
);

alter table sys_dict comment '系统字典表';

/*==============================================================*/
/* Table: sys_login_log                                         */
/*==============================================================*/
create table sys_login_log
(
    id                   varchar(63) not null,
    username             varchar(15) not null comment '用户名',
    log_type             tinyint comment '1--登录成功； 2--登录失败； 3--退出登录',
    remark               varchar(15) comment '备注',
    create_user_id       varchar(63) comment '创建人ID',
    create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
    primary key (id)
);

alter table sys_login_log comment '系统登录日志表';

/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
    id                   varchar(64) not null comment '主键',
    pid                  varchar(64) not null default '0' comment '上级ID',
    icon                 varchar(16) comment 'icon',
    code                 varchar(32) comment '权限代码',
    name                 varchar(64) not null comment '名称',
    status               tinyint default 0 comment '状态（0--可用；1--未用）',
    operation_url        varchar(255) comment '请求地址',
    type                 tinyint default 1 comment '是否为菜单（1--菜单；2--按钮）',
    order_num            tinyint default 1 comment '排序优先级',
    del_flag             tinyint not null default 0 comment '是否删除（0--否；1--是）',
    create_user_id       varchar(64) comment '填表人ID',
    update_user_id       varchar(64) comment '最后更新人ID',
    create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
    update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    app_code             varchar(16) comment '所属应用代码',
    primary key (id)
);

alter table sys_permission comment '菜单、按钮表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
    id                   varchar(64) not null,
    role_name            varchar(64) not null comment '角色名',
    role_code            varchar(32) comment '角色代码',
    role_type            tinyint not null default 2 comment '角色类型',
    del_flag             tinyint not null default 0 comment '是否删除（0--否；1--是）',
    description          varchar(255) comment '释义',
    status               tinyint not null default 0 comment '状态（0--正常；1--停用）',
    create_time          timestamp not null default CURRENT_TIMESTAMP comment '注册时间',
    update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    create_user_id       varchar(64) comment '填表人ID',
    update_user_id       varchar(64) comment '最后更新人ID',
    app_code             varchar(16) comment '所属应用代码',
    primary key (id),
    unique key uq_sys_role_colde_df (role_code, del_flag)
);

alter table sys_role comment '系统角色表';

/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
    id                   varchar(64) not null comment '主键',
    role_id              varchar(64) not null comment '角色ID',
    permission_id        varchar(64) not null comment '权限ID',
    create_user_id       varchar(64) comment '创建人ID',
    create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
    primary key (id),
    unique key uk_role_permission_role_id_permission_id (role_id, permission_id)
);

alter table sys_role_permission comment '角色权限关联表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
    id                   varchar(64) not null,
    username             varchar(16) not null comment '用户名',
    password             varchar(64) not null comment '密码',
    nickname             varchar(64) not null default '昵称' comment '昵称',
    avatar               varchar(255) comment '头像地址',
    email                varchar(255) comment '邮箱',
    phone                varchar(16) comment '联系电话',
    sex                  tinyint not null default 3 comment '性别（0--女；1--男；2--未知的性别；3--未说明的性别）',
    department_id        varchar(64) comment '所属部门ID',
    del_flag             tinyint not null default 0 comment '是否删除（0--否；1--是）',
    status               tinyint not null default 0 comment '状态（0--正常；1--停用）',
    last_time            timestamp not null default CURRENT_TIMESTAMP comment '最后操作时间',
    create_time          timestamp not null default CURRENT_TIMESTAMP comment '注册时间',
    update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    create_user_id       varchar(64) comment '填表人ID',
    update_user_id       varchar(64) comment '���后更新人ID',
    app_code             varchar(16) comment '所属应用代码',
    primary key (id),
    unique key uk_username_df (username, del_flag)
);

alter table sys_user comment '系统用户表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
    id                   varchar(64) not null comment '主键',
    user_id              varchar(64) not null comment '用户ID',
    role_id              varchar(64) not null comment '角色ID',
    create_user_id       varchar(64) comment '创建人ID',
    create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
    primary key (id),
    unique key uk_user_role_user_id_role_id (user_id, role_id)
);

alter table sys_user_role comment '系统用户角色表';


insert into demo.sys_role(role_name, role_type, description) VALUE ('超级管理员', 0, '超级管理员');
insert into demo.sys_role(role_name, role_type, description) VALUE ('管理员', 0, '管理员');

insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (1, 0, 'lock', '管理员管理', '/admin');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (101, 1, '', '页面权限', '/admin/page');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (102, 1, '', '指令权限', '/admin/directive');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (103, 1, '', '角色管理', '/admin/role');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (104, 1, '', '权限管理', '/admin/permission');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (2, 0, '', '用户管理', '/user');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (201, 2, '', '用户列表', '/user/list');

