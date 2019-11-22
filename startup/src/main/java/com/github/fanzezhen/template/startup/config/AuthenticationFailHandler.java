package com.github.fanzezhen.template.startup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fanzezhen.template.common.constant.ResultTypeConstant;
import com.github.fanzezhen.template.common.enums.exception.CommonBizExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        log.info("登录失败:" + exception.getMessage());
        log.info("username=>" + request.getParameter("username"));

        if (ResultTypeConstant.LOGIN_FAILED_RETURN_JSON) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", CommonBizExceptionEnum.BizExceptionEnum.AUTH_REQUEST_ERROR.getCode());
            map.put("msg", CommonBizExceptionEnum.BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage());
            map.put("data", exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        } else {
            super.setDefaultFailureUrl("/login/index?error=true"); // 登录失败，跳转到登录界面
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
