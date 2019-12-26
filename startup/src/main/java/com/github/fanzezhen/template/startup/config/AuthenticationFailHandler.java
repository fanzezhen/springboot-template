package com.github.fanzezhen.template.startup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fanzezhen.template.common.constant.CommonConstant;
import com.github.fanzezhen.template.common.constant.ResultTypeConstant;
import com.github.fanzezhen.template.common.enums.SysLogEnum;
import com.github.fanzezhen.template.common.enums.exception.CommonBizExceptionEnum;
import com.github.fanzezhen.template.pojo.entry.SysLoginLog;
import com.github.fanzezhen.template.service.SysLoginLogService;
import com.github.fanzezhen.template.service.thread.LogThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private SysLoginLogService sysLoginLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        CommonConstant.SYS_EXECUTOR.execute(new LogThread<>(sysLoginLogService, new SysLoginLog("",
                request.getParameter("username"), SysLogEnum.LoginLogTypeEnum.LOGIN_FAILED.getCode(),
                exception.getMessage()), new String[]{"登录失败:" + exception.getMessage(),
                "username=>" + request.getParameter("username")}));

        if (ResultTypeConstant.LOGIN_FAILED_RETURN_JSON) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", CommonBizExceptionEnum.BizExceptionEnum.AUTH_REQUEST_ERROR.getCode());
            map.put("msg", CommonConstant.LOGIN_FAILED_MESSAGE);
            map.put("data", exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        } else {
            super.setDefaultFailureUrl(CommonConstant.LOGIN_ADDRESS + "?error=true&data=" + java.net.URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8)); // 登录失败，跳转到登录界面
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
