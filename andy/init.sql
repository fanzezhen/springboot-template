CREATE DATABASE IF NOT EXISTS demo DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use demo;

create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
    id             bigint      not null auto_increment,
    username       varchar(16) not null comment '用户名',
    password       varchar(64) not null comment '密码',
    nickname       varchar(64) not null default '昵称' comment '昵称',
    avatar         varchar(255) comment '头像地址',
    email          varchar(255) comment '邮箱',
    phone          varchar(16) comment '联系电话',
    sex            smallint    not null default 3 comment '性别（0--女；1--男；2--未知的性别；3--未说明的性别）',
    del_flag       smallint    not null default 0 comment '是否删除（0--否；1--是）',
    status         smallint    not null default 0 comment '状态（0--正常；1--停用）',
    last_time      timestamp   not null default CURRENT_TIMESTAMP comment '最后操作时间',
    create_user_id bigint comment '填表人ID',
    update_user_id bigint comment '最后更新人ID',
    create_time    timestamp            default CURRENT_TIMESTAMP comment '创建时间',
    update_time    timestamp            default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    primary key (id),
    unique key uk_username_df (username, del_flag)
)
    auto_increment = 10000;

alter table sys_user
    comment '系统用户表';


/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
    id             bigint      not null auto_increment,
    role_name      varchar(64) not null comment '角色名',
    role_type      smallint    not null comment '角色类型',
    del_flag       smallint    not null default 0 comment '是否删除（0--否；1--是）',
    description    varchar(255) comment '释义',
    status         smallint    not null default 0 comment '状态（0--正常；1--停用）',
    create_time    timestamp   not null default CURRENT_TIMESTAMP comment '注册时间',
    update_time    timestamp   not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    create_user_id bigint comment '填表人ID',
    update_user_id bigint comment '最后更新人ID',
    primary key (id),
    unique key uk_role_name_type (role_name, del_flag)
)
    auto_increment = 10000;

alter table sys_role
    comment '系统角色表';


/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
    id             bigint not null auto_increment comment '主键',
    user_id        bigint not null comment '用户ID',
    role_id        bigint not null comment '角色ID',
    create_user_id bigint comment '创建人ID',
    create_time    timestamp default CURRENT_TIMESTAMP comment '创建时间',
    primary key (id),
    unique key uk_user_role_user_id_role_id (user_id, role_id)
)
    auto_increment = 10000;

alter table sys_user_role
    comment '系统用户角色表';

drop table sys_permission;
/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
    id             bigint      not null auto_increment comment '主键',
    pid            bigint      not null default 0 comment '上级ID',
    icon           varchar(16) comment '图标',
    name           varchar(64) not null comment '名称',
    status         smallint             default 0 comment '状态（0--可用；1--未用）',
    operation_url  varchar(255) comment '请求地址',
    type           smallint             default 1 comment '是否为菜单（1--菜单；2--按钮）',
    order_num      smallint             default 1 comment '排序优先级',
    del_flag       smallint    not null default 0 comment '是否删除（0--否；1--是）',
    create_user_id bigint comment '填表人ID',
    update_user_id bigint comment '最后更新人ID',
    create_time    timestamp            default CURRENT_TIMESTAMP comment '创建时间',
    update_time    timestamp            default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    primary key (id)
)
    auto_increment = 10000;

alter table sys_permission
    comment '菜单、按钮表';


/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
    id             bigint not null auto_increment comment '主键',
    role_id        bigint not null comment '角色ID',
    permission_id  bigint not null comment '权限ID',
    create_user_id bigint comment '创建人ID',
    create_time    timestamp default CURRENT_TIMESTAMP comment '创建时间',
    primary key (id),
    unique key uk_role_permission_role_id_permission_id (role_id, permission_id)
)
    auto_increment = 10000;

alter table sys_role_permission
    comment '角色权限关联表';

insert into demo.sys_role(role_name, role_type, description) VALUE ('超级管理员', 0, '超级管理员');
insert into demo.sys_role(role_name, role_type, description) VALUE ('管理员', 0, '管理员');

insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (1, 0, 'lock', '权限配置', '/permission');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (101, 1, '', '页面权限', '/permission/page');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (102, 1, '', '指令权限', '/permission/directive');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (103, 1, '', '角色权限', '/permission/role');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (2, 0, '', '用户管理', '/user');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (201, 2, '', '用户列表', '/user/list');

