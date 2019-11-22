package com.github.fanzezhen.template.service;

import com.github.fanzezhen.template.pojo.entry.SysUser;
import com.github.fanzezhen.template.pojo.model.SysUserDetail;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SysUserService extends UserDetailsService, AuthenticationUserDetailsService<CasAssertionAuthenticationToken>, BaseService<SysUser> {
    SysUserDetail loadUserByUsername(String username) throws UsernameNotFoundException;
    SysUser findByUsername(String username);
}
