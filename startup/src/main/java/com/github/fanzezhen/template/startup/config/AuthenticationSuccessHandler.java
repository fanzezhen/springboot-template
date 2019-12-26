package com.github.fanzezhen.template.startup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fanzezhen.template.common.constant.CommonConstant;
import com.github.fanzezhen.template.common.constant.ResultTypeConstant;
import com.github.fanzezhen.template.common.enums.SysLogEnum;
import com.github.fanzezhen.template.pojo.entry.SysLoginLog;
import com.github.fanzezhen.template.pojo.util.SysSecurityUtil;
import com.github.fanzezhen.template.service.SysLoginLogService;
import com.github.fanzezhen.template.service.thread.LogThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private SysLoginLogService sysLoginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CommonConstant.SYS_EXECUTOR.execute(new LogThread<>(sysLoginLogService, new SysLoginLog(
                SysSecurityUtil.getSysUserDetail().getId(), request.getParameter("username"),
                SysLogEnum.LoginLogTypeEnum.LOGIN_SUCCEED.getCode(), ""),
                new String[]{"登录成功: username=>" + request.getParameter("username")}));

        if (ResultTypeConstant.LOGIN_SUCCESS_RETURN_JSON) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", "200");
            map.put("msg", "登录成功");
            map.put("data", authentication);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        } else {
            super.setDefaultTargetUrl(CommonConstant.HOME_ADDRESS); // 设置默认登陆成功的跳转地址
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
