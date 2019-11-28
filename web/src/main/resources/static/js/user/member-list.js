layui.use(['element', 'form', 'laypage'], function () {
    const $ = layui.$;
    const element = layui.element;
    const form = layui.form;
    const laypage = layui.laypage;
    let current = 1;    // 当前页
    let size = 5;   // 每页条数
    let total = 0; // 总条数

    //监听提交
    form.on('submit(search)', function () {
        User.searchPage();
        return false;   // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    User.queryData = function () {
        const searchForm = form.val("searchForm");
        return {
            "current": current,
            "size": size,
            "startDate": searchForm.start,
            "endDate": searchForm.end,
            "param": searchForm
        };
    };


    User.searchPage = function () {
        ajaxCommit("post", false, "/user/list/page", JSON.stringify(User.queryData()), pageSuccess, alert_error, "application/json;charset=utf-8")
    };

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
            $tr.append($("<td class=\"td-status\">" + CommonEnum.STATUS[user.status].html + "</td>"));
            $tr.append($("<td>" + new Date(user.createTime).Format("yyyy-MM-dd hh:mm:ss") + "</td>"));
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

        total = data.total;
        User.paged();
        element.init();
        form.render();
    }

    /**
     * 分页方法
     */
    User.paged = function () {
        laypage.render({
            elem: 'ym',
            count: total, //数据总数，从服务端得到
            curr: current,
            limit: size,
            layout: ['prev', 'page', 'next', 'count', 'skip'],
            jump: function (obj, first) {
                current = obj.curr;
                total = obj.count;
                //首次不执行
                if (!first) {
                    User.searchPage();
                }
            }
        });
    };

    User.searchPage();
});
