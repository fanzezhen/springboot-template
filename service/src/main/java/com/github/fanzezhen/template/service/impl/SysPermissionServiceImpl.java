package com.github.fanzezhen.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.fanzezhen.template.dao.SysPermissionDao;
import com.github.fanzezhen.template.pojo.entry.SysPermission;
import com.github.fanzezhen.template.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    public SysPermission findById(String id) {
        return sysPermissionDao.selectById(id);
    }

    @Override
    public List<SysPermission> findByUserId(String userId) {
        return null;
    }

    @Override
    public List<SysPermission> findAll() {
        return sysPermissionDao.selectList(new QueryWrapper<>());
    }

    @Override
    public List<SysPermission> listByRoleIds(Collection<String> roleIds) {
        return (roleIds == null || roleIds.size() <= 0) ? new ArrayList<>() : sysPermissionDao.listByRoleIds(roleIds);
    }
}
