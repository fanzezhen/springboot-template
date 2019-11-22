package com.github.fanzezhen.template.web;

import com.github.fanzezhen.template.common.enums.exception.CommonBizExceptionEnum;
import com.github.fanzezhen.template.common.exception.ServiceException;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.pojo.model.SysUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            // 抛出为登录异常, 后台根据对应异常跳转到登录页
            throw new ServiceException(CommonBizExceptionEnum.BizExceptionEnum.SESSION_TIMEOUT);
        }
        return session;
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    protected Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication;
        } else {
            throw new ServiceException(CommonBizExceptionEnum.BizExceptionEnum.SESSION_TIMEOUT);
        }
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    protected User getUser() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) getAuthentication();
        //details里面可能存放了当前登录用户的详细信息，也可以通过cast后拿到
        User userDetails = (User) authenticationToken.getDetails();
        if (userDetails == null) throw new ServiceException(CommonBizExceptionEnum.BizExceptionEnum.SESSION_TIMEOUT);
        return userDetails;
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    protected SysUserDetail getSysUserDetail() {
        try {
            return (SysUserDetail) getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new ServiceException(CommonBizExceptionEnum.BizExceptionEnum.SESSION_TIMEOUT);
        }
    }

    /**
     * 通用Result
     */
    protected JsonResult createJsonResult(){
        HashMap<String, String> result = new HashMap<>();
        result.put("status", "success");
        return new JsonResult<>(result);
    }
}
