# 基于SpringBoot-Security、MyBatis、Thymeleaf的项目模板

备注
    
    原本使用JPA作为数据库连接框架， 但国内项目用到复杂查询的场景和次数太多了， 用JPA明显不如使用MyBatis-Plus配合xml文件合适
    
problems:

    1. post请求返回403
    经排查属于csrf相关问题，解决方法：
        在html头部添加
              <meta name="_csrf" th:content="${_csrf.token}"/>
              <meta name="_csrf_header" th:content="${_csrf.headerName}"/>
        form中添加 th:action
            <form action="#" th:action="@{/URL} + '?' + ${_csrf.parameterName} + '=' + ${_csrf.token}" ...>
        ajax请求前添加
            const token = $("meta[name='_csrf']").attr("content");
            const header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

