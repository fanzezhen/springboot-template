package com.github.fanzezhen.template.service;

import com.github.fanzezhen.template.pojo.entry.SysRolePermission;

import java.util.List;
import java.util.Set;

public interface SysRolePermissionService extends BaseService<SysRolePermission>{
    List<SysRolePermission> saveAll(List<SysRolePermission> sysRolePermissionList);
    public void delete(SysRolePermission sysRolePermission);
    public void deleteInBatch(Set<SysRolePermission> sysRolePermissionList);
}
