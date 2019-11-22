package com.github.fanzezhen.template.dao;

import com.github.fanzezhen.template.pojo.entry.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleDao extends BaseDao<SysRole> {
    List<SysRole> listByUserId(Long userId);
}
