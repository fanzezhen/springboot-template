package com.github.fanzezhen.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.fanzezhen.template.dao.SysRolePermissionDao;
import com.github.fanzezhen.template.pojo.entry.SysRolePermission;
import com.github.fanzezhen.template.service.SysRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRolePermissionServiceImpl extends BaseServiceImpl<SysRolePermissionDao, SysRolePermission> implements SysRolePermissionService {
    @Resource
    private SysRolePermissionDao sysRolePermissionDao;

    @Transactional
    public List<SysRolePermission> saveAll(List<SysRolePermission> sysRolePermissionList){
        for (SysRolePermission sysRolePermission : sysRolePermissionList){
            sysRolePermissionDao.insert(sysRolePermission);
        }
        return sysRolePermissionList;
    }

    @Override
    public void delete(SysRolePermission sysRolePermission) {
        sysRolePermissionDao.delete(new QueryWrapper<>(sysRolePermission));
    }

    @Override
    public void deleteInBatch(Set<SysRolePermission> sysRolePermissionList){
        Set<String> idList = new HashSet<>();
        for (SysRolePermission sysRolePermission : sysRolePermissionList){
            idList.add(sysRolePermission.getId());
        }
        sysRolePermissionDao.deleteBatchIds(idList);
    }
}
