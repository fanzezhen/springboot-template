const AdminConstant = {
    API: {BASE: {URL: "/admin", TYPE: "html", DESC: "管理员首页"}}
};
// 初始化API接口列表
AdminConstant.API["ROLE"] = {URL: AdminConstant.API.BASE.URL + "/role", TYPE: "html", DESC: "角色管理"};
AdminConstant.API["ROLE_LIST_PAGE"] = {
    URL: AdminConstant.API.BASE.URL + "/role/list/page",
    TYPE: "data",
    DESC: "用户列表分页查询"
};
AdminConstant.API["ROLE_EDIT"] = {URL: AdminConstant.API.BASE.URL + "/role/edit", TYPE: "html", DESC: "编辑角色"};
AdminConstant.API["ROLE_SAVE"] = {URL: AdminConstant.API.BASE.URL + "/save", TYPE: "data", DESC: "保存用户"};
AdminConstant.API["ROLE_DEL_BATCH"] = {
    URL: AdminConstant.API.BASE.URL + "/role/del/batch",
    TYPE: "data",
    DESC: "批量删除"
};

const AdminEnum = {
    ROLE_TYPE: {0: "超级管理员", 1: "管理员", 2: "普通角色", 3: "访客角色"},
};

const Admin = {};
