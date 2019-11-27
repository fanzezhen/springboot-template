const userEnum = {
    SEX: {0: "女", 1: "男", 2: "未知的性别", 3: "未说明的性别"},
};
// const userSexEnum = {0: "女", 1: "男", 2: "未知的性别", 3: "未说明的性别"};

function searchPage() {
    ajaxCommit("post", false, "/user/list/page", JSON.stringify(""), pageSuccess, alert_error, "application/json;charset=utf-8")
}

function pageSuccess(data) {
    const $tbody = $("#tbody");
    $tbody.children().filter('tr').remove();
    data.records.forEach(function (user) {
        const $tr = $("<tr></tr>"); // 生成一个 tr 元素
        $tr.append($("<td><input type=\"checkbox\" name=\"id\" value=\"1\" lay-skin=\"primary\"></td>"));
        $tr.append($("<td>" + user.id + "</td>"));
        $tr.append($("<td>" + user.username + "</td>"));
        $tr.append($("<td>" + user.nickname + "</td>"));
        $tr.append($("<td>" + userEnum.SEX[user.sex] + "</td>"));
        $tr.append($("<td>" + CommonEnum.STATUS[user.status] + "</td>"));
        const $tdManage = $(document.createElement('td')); // 生成一个 td 元素
        $tdManage.append($("<a onclick=\"member_stop(this,'10001')\" href=\"javascript:;\" title=\"启用\">\n" +
            "                  <i class=\"layui-icon\">&#xe601;</i>\n" +
            "                </a>"));
        $tdManage.append($("<a title=\"编辑\" onclick=\"xadmin.open('编辑','member-edit.html',600,400)\" href=\"javascript:;\">\n" +
            "                  <i class=\"layui-icon\">&#xe642;</i>\n" +
            "                </a>"));
        $tdManage.append($("<a onclick=\"xadmin.open('修改密码','member-password.html',600,400)\" title=\"修改密码\" href=\"javascript:;\">\n" +
            "                  <i class=\"layui-icon\">&#xe631;</i>\n" +
            "                </a>"));
        $tdManage.append($("<a title=\"删除\" onclick=\"member_del(this,'要删除的id')\" href=\"javascript:;\">\n" +
            "                  <i class=\"layui-icon\">&#xe640;</i>\n" +
            "                </a>"));
        $tr.append($tdManage);
        $tbody.append($tr);
    });
    paged("page", data.current, data.pages);
}
