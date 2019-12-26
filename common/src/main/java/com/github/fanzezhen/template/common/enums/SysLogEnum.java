package com.github.fanzezhen.template.common.enums;

public class SysLogEnum {
    /**
     * @desc (登录日志类型枚举).
     */
    public enum LoginLogTypeEnum {
        LOGIN_SUCCEED(1, "登录成功"),
        LOGIN_FAILED(2, "登录失败"),
        LOGOUT(3, "退出登录");

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        LoginLogTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
