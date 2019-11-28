layui.use(['form', 'layer', 'jquery'], function () {
    const $ = layui.jquery;
    const form = layui.form, layer = layui.layer;

    //自定义验证规则
    form.verify({
        username: function (value) {
            if (value.length < 6) {
                return '登录名至少得6个字符啊';
            }
        },
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
        rePass: function () {
            if ($('#L_pass').val() !== $('#L_rePass').val()) {
                return '两次密码不一致';
            }
        }
    });

    User.successAdd = function () {
        layer.alert("增加成功", {
                icon: 6
            },
            function () {
                //关闭当前frame
                xadmin.close();

                // 可以对父窗口进行刷新
                xadmin.father_reload();
            });
    };

    //监听提交
    form.on('submit(add)', function (data) {
        ajaxCommit("post", false, "/user/save", JSON.stringify(data.field), User.successAdd, alert_error, "application/json;charset=utf-8");
        return false;
    });

});
