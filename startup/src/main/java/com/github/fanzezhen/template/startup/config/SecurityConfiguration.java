package com.github.fanzezhen.template.startup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fanzezhen.template.common.enums.MemoryEnum;
import com.github.fanzezhen.template.common.enums.RoleEnum;
import com.github.fanzezhen.template.common.enums.exception.CommonBizExceptionEnum;
import com.github.fanzezhen.template.service.SysUserService;
import com.github.fanzezhen.template.startup.interceptor.MyFilterSecurityInterceptor;
import com.github.fanzezhen.template.startup.session.ExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    private DataSource dataSource;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailHandler authenticationFailHandler;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    /**
     * 向AuthenticationManager添加Provider
     *
     * @return
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.sysUserService).passwordEncoder(passwordEncoder());
    }

    /**
     * 注入UserAuthenticationProvider
     *
     * @return
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(sysUserService);
        return daoAuthenticationProvider;
    }

    //密码加密配置
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenDaository() {
        JdbcTokenRepositoryImpl tokenDaository = new JdbcTokenRepositoryImpl();
        tokenDaository.setDataSource(dataSource);
        // tokenDaository.setCreateTableOnStartup(true);
        // create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)
        return tokenDaository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 定义认证规则,管理帐号/密码
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //passwordEncoder是对密码的加密处理，如果user中密码没有加密，则可以不加此方法。注意加密请使用security自带的加密方式。
        auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
        //学习用内存认证
        auth.inMemoryAuthentication()
                .withUser(MemoryEnum.MemoryAuthEnum.GUEST.getUsername())
                .password(new BCryptPasswordEncoder().encode(MemoryEnum.MemoryAuthEnum.GUEST.getPassword()))
                .authorities(MemoryEnum.MemoryAuthEnum.GUEST.getRoleTypes())
                .and().withUser(MemoryEnum.MemoryAuthEnum.ROOT.getUsername())
                .password(new BCryptPasswordEncoder().encode(MemoryEnum.MemoryAuthEnum.ROOT.getPassword()))
                .authorities(MemoryEnum.MemoryAuthEnum.ROOT.getRoleTypes());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()    //启用http 基础验证
                //未登录时，进行json格式的提示
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", CommonBizExceptionEnum.BizExceptionEnum.NOT_LOGIN.getCode());
                    map.put("message", CommonBizExceptionEnum.BizExceptionEnum.NOT_LOGIN.getMessage());
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                .and()
                .cors() //添加cors支持跨域
                .and()
                .csrf().disable()//禁用 csrf 功能
                .authorizeRequests()//限定签名成功的请求
                .antMatchers("/login").permitAll() //登录页面不限定
                .antMatchers("/admin/**") //对admin下的接口 需要ADMIN权限
                .hasAnyAuthority(RoleEnum.RoleTypeEnum.ADMIN.getCode(), RoleEnum.RoleTypeEnum.SPECIAL_ADMIN.getCode())
                .antMatchers("/oauth/**").permitAll()//不拦截 oauth 开放的资源
                .anyRequest().authenticated()//其他没有限定的请求，登录后才允许访问
                .and()
                .formLogin()//使用 spring security 默认登录页面
                .successHandler(authenticationSuccessHandler)   //登陆成功后的操作
                .failureHandler(authenticationFailHandler) //登录失败处理
                .and().logout().permitAll() //注销行为任意访问
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenDaository())
                .tokenValiditySeconds(24 * 60 * 60) // 过期秒数
                .userDetailsService(sysUserService)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false) // 当达到maximumSessions时，true表示不能踢掉前面的登录，false表示踢掉前面的用户
                .expiredSessionStrategy(new ExpiredSessionStrategy()) // 当达到maximumSessions时，踢掉前面登录用户后的操作
        ;
//        http.cors();//添加cors支持跨域
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.sessionManagement().maximumSessions(1).expiredUrl("/security/login");        //防止多处登录
    }
}
