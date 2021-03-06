package com.github.fanzezhen.template.startup.captcha;

import com.github.fanzezhen.template.common.constant.SecurityConstant;
import com.github.fanzezhen.template.common.exception.ValidateCodeException;
import com.github.fanzezhen.template.pojo.model.ImageCode;
import com.github.fanzezhen.template.startup.config.AuthenticationFailHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fanzezhen on 2019/7/26
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Resource
    private AuthenticationFailHandler authenticationFailHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase("/login", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
            try {
                validateCode(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                authenticationFailHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, ValidateCodeException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, SecurityConstant.SESSION_KEY_CAPTCHA);
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("????????????????????????");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("?????????????????????");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest, SecurityConstant.SESSION_KEY_CAPTCHA);
            throw new ValidateCodeException("?????????????????????");
        }
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("?????????????????????");
        }
        sessionStrategy.removeAttribute(servletWebRequest, SecurityConstant.SESSION_KEY_CAPTCHA);

    }
}
