package com.github.fanzezhen.template.startup.config;

import com.github.fanzezhen.common.enums.exception.CommonBizExceptionEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限不足自定403返回值
 *
 * @author xuwang
 * Created on 2019/05/29 16:10.
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(CommonBizExceptionEnum.BizExceptionEnum.PERMISSION_DENIED.getCode());
    }
}
