package com.github.fanzezhen.template.pojo.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "sys_user")
public class SysUser extends BaseAloneEntry {
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String nickname;
    private Integer sex;
    private String avatar;  //头像
    private String email;
    private String phone;
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date lastTime;  //最后操作时间

    public SysUser(String username) {
        setUsername(username);
    }

    public void init(SysUser sysUser) {
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
        this.nickname = sysUser.getNickname();
        this.avatar = sysUser.getAvatar();
        this.email = sysUser.getAvatar();
        this.phone = sysUser.getPhone();
        this.lastTime = sysUser.getLastTime();
        super.init(new BaseAloneEntry(sysUser.getStatus(), sysUser.getDelFlag(), sysUser.getUpdateTime(), sysUser.getUpdateUserId()));
    }

    public void init() {
        super.init(new BaseAloneEntry(0, 0, new Date(), this.getUpdateUserId()));
    }

    public SysUser(String username, String password, String nickname, String avatar, String email, String phone, Date lastTime, Set<SysUserRole> sysUserRoleSet) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.lastTime = lastTime;
    }
}
