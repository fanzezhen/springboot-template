package com.github.fanzezhen.template.service;

import com.github.fanzezhen.template.pojo.entry.SysUserRole;

import java.util.Set;

public interface SysUserRoleService extends BaseService<SysUserRole>{
    public void deleteInBatch(Set<SysUserRole> sysUserRoleSet);
}
