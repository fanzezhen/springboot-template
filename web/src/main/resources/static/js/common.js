const homeUrl = "/hello";
const CommonEnum = {
    STATUS: {0: "启用", 1: "禁用"}
};

function alert_info(info) {
    top.layer.msg(info, {icon: 6});
}

function alert_success(info) {
    top.layer.msg(JSON.stringify(info), {icon: 1});
}

function alert_error(info) {
    top.layer.msg(JSON.stringify(info), {icon: 2});
}

function alert_confirm(tip, ensure) {
    top.layer.confirm(tip, {
        skin: 'layui-layer-admin'
    }, function (index) {
        ensure(index);
    });
}

function redirect(url, isNew) {
    if (isNew) {
        window.open(url)
    } else {
        window.location.href = url
    }
}

function mainPage() {
    window.location.href = homeUrl
}

/**
 * 公共ajax提交
 * @param type
 * @param init
 * @param url
 * @param data
 * @param success
 * @param error
 * @param contentType
 */
function ajaxCommit(type, init, url, data, success, error, contentType) {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $.ajax({
        async: true,
        type: type,
        url: url,
        data: data,
        contentType: contentType,
        beforeSend: function () {
            if (!init)
                top.layer.load(0, {shade: [0.4, '#fff']});
        },
        success: function (result) {
            success(result);
            if (top.layer !== undefined)
                top.layer.closeAll('loading');
        },
        error: function (result) {
            if (error) {
                error(result);
            } else {
                if (result.responseJSON != null) {
                    alert_error(result.responseJSON.message + "!");
                } else {
                    alert_error("请求失败!");
                }
            }
            if (top.layer !== undefined)
                top.layer.closeAll('loading');
        }
    });
}

/**
 * 页面滑动定位到某元素
 * @param elementId
 */
function scrollTo(elementId) {
    $(document.getElementById(elementId)).parent()[0].scrollIntoView({behavior: "smooth", block: "center"})
}

/**
 * 获取URL中指定参数
 * @param name
 * @returns {string|boolean}
 */
function getUrlParamByName(name) {
    // 取得url中'?'后面的字符
    var query = window.location.search.substring(1);
    // 把参数按&拆分成数组
    var param_arr = query.split("&");
    for (var i = 0; i < param_arr.length; i++) {
        var pair = param_arr[i].split("=");
        if (pair[0] === name) {
            return pair[1];
        }
    }
    return false;
}

function paged(pageId, current, pages) {
    const $page = $("#" + pageId);
    $page.html("<a class=\"prev\" href=\"\">&lt;&lt;</a>");
    for (let i = -2; i <= 2; i += 1) {
        const index = current + i;
        if (index > 0 && index <= pages) {
            if (index !== current)
                $page.append("<a class=\"num\" href=\"\">" + index + "</a>");
            else
                $page.append("<span class=\"current\">" + index + "</span>");
        }
    }
    $page.append("<a class=\"next\" href=\"\">&gt;&gt;</a>");
}
