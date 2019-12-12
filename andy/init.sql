CREATE DATABASE IF NOT EXISTS demo DEFAULT character set utf8mb4 collate utf8mb4_unicode_ci;
use demo;

create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);


insert into demo.sys_role(role_name, role_type, description) VALUE ('超级管理员', 0, '超级管理员');
insert into demo.sys_role(role_name, role_type, description) VALUE ('管理员', 0, '管理员');

insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (1, 0, 'lock', '管理员管理', '/admin');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (101, 1, '', '页面权限', '/admin/page');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (102, 1, '', '指令权限', '/admin/directive');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (103, 1, '', '角色管理', '/admin/role');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (104, 1, '', '权限管理', '/admin/permission');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (2, 0, '', '用户管理', '/user');
insert into demo.sys_permission(id, pid, icon, name, operation_url) VALUES (201, 2, '', '用户列表', '/user/list');

