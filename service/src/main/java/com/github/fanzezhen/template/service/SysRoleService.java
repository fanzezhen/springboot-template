package com.github.fanzezhen.template.service;

import com.github.fanzezhen.template.pojo.entry.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends BaseService<SysRole>{
    SysRole findById(String id);
    SysRole findByName(String name);
    void deleteById(String id);
    void delete(SysRole sysRole);
    void deleteInBatch(List<String> idList);
    List<SysRole> findAll();
    List<SysRole> listByUserId(String userId);
}
