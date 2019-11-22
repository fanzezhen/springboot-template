package com.github.fanzezhen.template.common.constant;

public class CommonConstant extends com.github.fanzezhen.common.constant.CommonConstant{
    public static final String LOGIN_ADDRESS = "/login";
    public static final String ADMIN_ADDRESS_PATTERN = "/admin/**";
    public static final String OAUTH_ADDRESS_PATTERN = "/oauth/**";
    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

//    public static final String TOKEN_KEY = "Admin-Token";
    public static final String TOKEN_KEY = "x-token";
    public static final String TOKEN_PREFIX = "Bearer ";
}
