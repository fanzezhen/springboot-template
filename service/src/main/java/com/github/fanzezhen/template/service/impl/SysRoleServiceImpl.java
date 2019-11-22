package com.github.fanzezhen.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.fanzezhen.template.dao.SysRoleDao;
import com.github.fanzezhen.template.pojo.entry.SysRole;
import com.github.fanzezhen.template.service.SysRolePermissionService;
import com.github.fanzezhen.template.service.SysRoleService;
import com.github.fanzezhen.template.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public SysRole findById(Long id) {
        return sysRoleDao.selectById(id);
    }

    @Override
    public SysRole findByName(String name) {
        SysRole sysRoleParam = new SysRole();
        sysRoleParam.setRoleName(name);
        return sysRoleDao.selectOne(new QueryWrapper<>(sysRoleParam));
    }

    @Override
    public void deleteById(Long id) {
        sysRoleDao.deleteById(id);
        // todo delete sys_user_role and sys_role_permission
    }

    @Override
    @Transactional
    public void delete(SysRole sysRole) {
        sysRoleDao.delete(new QueryWrapper<>(sysRole));
    }

    @Override
    @Transactional
    public void deleteInBatch(List<Long> idList) {
        for (Long id : idList){
            deleteById(id);
        }
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleDao.selectList(new QueryWrapper<>());
    }

    @Override
    public List<SysRole> listByUserId(Long userId) {
        return sysRoleDao.listByUserId(userId);
    }
}
