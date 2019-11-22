package com.github.fanzezhen.template.common.constant;

public class SecuritySessionConstant {

    // 用户对应在session中key
    public static final String AUTHENTICATION_SESSION_KEY = "SECURITY_USER_ATTR";

    // 用户对应在session中key
    public static final String USER_SESSION_KEY = "SECURITY_USER_ATTR";

    // 公司对应在session中key
    public static final String COMPANY_SESSION_KEY = "SECURITY_COMPANY_ATTR";

    // 党政机关单位对应在session中key
    public static final String OFFICES_COMPANY_SESSION_KEY = "SECURITY_OFFICES_COMPANY_ATTR";

    // 中央在沪单位对应在session中key
    public static final String SHANGHAI_COMPANY_SESSION_KEY = "SECURITY_SHANGHAI_COMPANY_ATTR";

    // 地方单位对应在session中key
    public static final String LOCAL_COMPANY_SESSION_KEY = "SECURITY_LOCAL_COMPANY_ATTR";

    // 系统外单位对应在session中key
    public static final String OUT_SYS_COMPANY_SESSION_KEY = "SECURITY_OUT_SYS_COMPANY_ATTR";

    // 部门对应在session中的key
    public static final String DEPARTMENT_SESSION_KEY = "SECURITY_DEPARTMENT_ATTR";

    // 角色对应在session中的key
    public static final String ROLE_SESSION_KEY = "SECURITY_ROLE_ATTR";

    // 权限对应在session中的key
    public static final String PERMISSION_SESSION_KEY = "SECURITY_PERMISSION_ATTR";

    // 有查看权限的公司ID列表对应在session中的key
    public static final String COMPANY_ID_LIST_SESSION_KEY = "SECURITY_COMPANY_ID_LIST_ATTR";
}
