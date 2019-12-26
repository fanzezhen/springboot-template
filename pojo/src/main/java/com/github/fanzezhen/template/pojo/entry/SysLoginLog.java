package com.github.fanzezhen.template.pojo.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SysLoginLog extends BaseEntry{
    private String username;
    private Integer logType;    // 1--登录成功； 2--登录失败； 3--退出登录
    private String remark;

    public SysLoginLog(String createUserId, String username, Integer logType, String remark) {
        this.setCreateUserId(createUserId);
        this.username = username;
        this.logType = logType;
        this.remark = remark;
    }
}
