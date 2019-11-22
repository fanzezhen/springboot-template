package com.github.fanzezhen.template.service.impl;

import com.github.fanzezhen.template.dao.SysUserRoleDao;
import com.github.fanzezhen.template.pojo.entry.SysUserRole;
import com.github.fanzezhen.template.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {
    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public void deleteInBatch(Set<SysUserRole> sysUserRoleSet) {
        Set<Long> idList = new HashSet<>();
        for (SysUserRole sysUserRole:sysUserRoleSet) {
            idList.add(sysUserRole.getId());
        }
        sysUserRoleDao.deleteBatchIds(idList);
    }
}
