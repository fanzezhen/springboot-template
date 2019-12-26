package com.github.fanzezhen.template.pojo.util;

import com.github.fanzezhen.template.common.enums.exception.CommonBizExceptionEnum;
import com.github.fanzezhen.template.common.exception.ServiceException;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.pojo.model.SysUserDetail;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;

public class SysSecurityUtil {
    /**
     * 获取登录用户
     *
     * @return
     */
    public static Authentication getAuthentication() {
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
    public static User getUser() {
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
    public static SysUserDetail getSysUserDetail() {
        try {
            return (SysUserDetail) getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new ServiceException(CommonBizExceptionEnum.BizExceptionEnum.SESSION_TIMEOUT);
        }
    }

    /**
     * 通用Result
     */
    public static JsonResult<HashMap<String, String>> createJsonResult(){
        HashMap<String, String> result = new HashMap<>();
        result.put("status", "success");
        return new JsonResult<>(result);
    }
}
