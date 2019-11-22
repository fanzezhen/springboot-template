package com.github.fanzezhen.template.pojo.entry;

import com.github.fanzezhen.template.pojo.model.SysRoleDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "sys_role")
public class SysRole extends BaseAloneEntry {
    private String roleName;
    private String description;
    private Integer roleType;

    public SysRole(SysRoleDetail sysRoleDetail) {
        setRoleName(sysRoleDetail.getRoleName());
        setDescription(sysRoleDetail.getDescription());
        if (sysRoleDetail.getRoleType() != null)
            setRoleType(sysRoleDetail.getRoleType());
        if (sysRoleDetail.getId() != null)
            setId(sysRoleDetail.getId());
        if (sysRoleDetail.getStatus() != null)
            setStatus(sysRoleDetail.getStatus());
        if (sysRoleDetail.getDelFlag() != null)
            setDelFlag(sysRoleDetail.getDelFlag());
    }
}
