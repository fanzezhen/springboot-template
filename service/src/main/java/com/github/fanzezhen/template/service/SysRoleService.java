package com.github.fanzezhen.template.service;

import com.github.fanzezhen.template.pojo.entry.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends BaseService<SysRole>{
    SysRole findById(Long id);
    SysRole findByName(String name);
    void deleteById(Long id);
    void delete(SysRole sysRole);
    void deleteInBatch(List<Long> idList);
    List<SysRole> findAll();
    List<SysRole> listByUserId(Long userId);
}
