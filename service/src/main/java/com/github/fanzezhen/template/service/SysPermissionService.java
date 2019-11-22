package com.github.fanzezhen.template.service;

import com.github.fanzezhen.template.pojo.entry.SysPermission;

import java.util.Collection;
import java.util.List;

public interface SysPermissionService extends BaseService<SysPermission>{
    SysPermission findById(Long id);
    List<SysPermission> findByUserId(Long userId);
    List<SysPermission> findAll();
    List<SysPermission> listByRoleIds(Collection<Long> roleIds);
}
