package com.github.fanzezhen.template.startup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fanzezhen.template.common.constant.CommonConstant;
import com.github.fanzezhen.template.common.enums.MemoryEnum;
import com.github.fanzezhen.template.common.enums.RoleEnum;
import com.github.fanzezhen.template.common.enums.exception.CommonBizExceptionEnum;
import com.github.fanzezhen.template.service.SysUserService;
import com.github.fanzezhen.template.startup.captcha.ValidateCodeFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

    @Resource
    private ValidateCodeFilter validateCodeFilter;

    /**
     * ???AuthenticationManager??????Provider
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
     * ??????UserAuthenticationProvider
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

    //??????????????????
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
     * ??????????????????,????????????/??????
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //passwordEncoder????????????????????????????????????user????????????????????????????????????????????????????????????????????????security????????????????????????
        auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
        //?????????????????????
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
                .httpBasic()    //??????http ????????????)
                .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                .and().cors() //??????cors????????????
                .and().csrf().disable()//?????? csrf ??????
                .authorizeRequests()//???????????????????????????
                .antMatchers("/admin/**")
                .hasAnyAuthority(RoleEnum.RoleTypeEnum.ADMIN.getCode(), RoleEnum.RoleTypeEnum.SPECIAL_ADMIN.getCode())
                .antMatchers("/oauth/**").permitAll()//????????? oauth ???????????????
                .antMatchers("/static/**").permitAll()//?????????????????????
                .anyRequest().authenticated()//??????????????????????????????????????????????????????
                .and()
                .formLogin().loginPage(CommonConstant.LOGIN_ADDRESS).loginProcessingUrl("/login").permitAll()//?????? spring security ??????????????????
                .successHandler(authenticationSuccessHandler)   //????????????????????????
                .failureHandler(authenticationFailHandler) //??????????????????
                .and().logout().permitAll() //????????????????????????
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenDaository())
                .tokenValiditySeconds(24 * 60 * 60) // ????????????
                .userDetailsService(sysUserService)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false) // ?????????maximumSessions??????true????????????????????????????????????false???????????????????????????
                .expiredSessionStrategy(new ExpiredSessionStrategy()) // ?????????maximumSessions??????????????????????????????????????????
        ;
//        http.cors();//??????cors????????????
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);   //???????????????
        http.sessionManagement().maximumSessions(1).expiredUrl("/security/login");        //??????????????????
    }
}
