package com.github.fanzezhen.template.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fanzezhen.template.pojo.entry.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SysPermissionDao extends BaseMapper<SysPermission> {
    List<SysPermission> listByRoleIds(Collection<String> roleIds);
}
