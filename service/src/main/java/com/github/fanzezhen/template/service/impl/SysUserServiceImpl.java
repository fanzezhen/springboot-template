package com.github.fanzezhen.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.fanzezhen.template.common.enums.RoleEnum;
import com.github.fanzezhen.template.dao.SysUserDao;
import com.github.fanzezhen.template.pojo.entry.*;
import com.github.fanzezhen.template.pojo.model.SysUserDetail;
import com.github.fanzezhen.template.service.SysPermissionService;
import com.github.fanzezhen.template.service.SysRoleService;
import com.github.fanzezhen.template.service.SysUserService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    public SysUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        //用户，用于判断权限，请注意此用户名和方法参数中的username一致；BCryptPasswordEncoder是用来演示加密使用。
        SysUser sysUser = sysUserDao.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        if (sysUser != null) {
            //生成环境是查询数据库获取username的角色用于后续权限判断（如：张三 admin)
            Set<GrantedAuthority> grantedAuthorities;
            Set<String> grantedAuthorityRoleSet = new HashSet<>();
            Set<Long> sysRoleIdSet = new HashSet<>();
            Set<String> roleNameSet = new HashSet<>();
            Set<Integer> roleTypeSet = new HashSet<>();

            List<SysRole> sysRoleList = sysRoleService.listByUserId(sysUser.getId());
            for (SysRole sysRole : sysRoleList) {
                sysRoleIdSet.add(sysRole.getId());
                roleNameSet.add(sysRole.getRoleName());
                roleTypeSet.add(sysRole.getRoleType());
            }
            for (SysPermission sysPermission : sysPermissionService.listByRoleIds(sysRoleIdSet)) {
                if (sysPermission.getId() != null) {
                    grantedAuthorityRoleSet.add("sys_permission_" + sysPermission.getId());
                }
            }
        grantedAuthorityRoleSet.addAll(RoleEnum.RoleTypeEnum.queryRoleTypeCodeSetByType(roleTypeSet));
        //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
        grantedAuthorities = new HashSet<>(AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.join(grantedAuthorityRoleSet, ',')));
        SysUserDetail sysUserDetail = new SysUserDetail(sysUser, grantedAuthorities);
        sysUserDetail.setRoles(sysRoleList);
        sysUserDetail.setRoleIds(sysRoleIdSet);
        sysUserDetail.setRoleNames(roleNameSet);
        return sysUserDetail;
    } else

    {
        throw new UsernameNotFoundException("user: " + username + " do not exist!");
    }

}

    @Override
    public SysUserDetail loadUserDetails(CasAssertionAuthenticationToken casAssertionAuthenticationToken) throws UsernameNotFoundException {
        // 结合具体的逻辑去实现用户认证，并返回继承UserDetails的用户对象;
        String username = casAssertionAuthenticationToken.getName();
        System.out.println("当前的用户名是：" + username);

        SysUserDetail userInfo = loadUserByUsername(username);

        System.out.println(userInfo.toString());
        return userInfo;
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserDao.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }
}
