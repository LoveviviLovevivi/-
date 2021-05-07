package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 当前类是一个配置类
@Configuration
// 启用web环境下的权限功能
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置 prePostEnabled = true。保证@PreAuthority、 @PostAuthority、@PreFilter、@PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class webAppScurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
// 无法在xxxservice中装配
//    @Bean
//    public BCryptPasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 临时使用内存版测试
//        builder.inMemoryAuthentication().withUser("jerry")
//                .password("123123").roles("ADMIN");
        // 基于数据库的正常使用
        builder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/admin/to/login/page.html","/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**"," /layer/**","/script/**","/ztree/**")
                .permitAll()
                .antMatchers("/admin/get/page.html")
                //.hasRole("经理")
                .access("hasRole('经理') OR hasAuthority('user:get')")
                .anyRequest() // 其他任意的请求
                .authenticated() // 认证后访问
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception", new  Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                    }
                })
                .and()
                .csrf() // csrf
                .disable() // 禁用
                .formLogin() // 开启表单登录功能
                .loginPage("/admin/to/login/page.html") // 指定登录页面
                .loginProcessingUrl("/security/do/login.html") // 指定处理登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html") // 登录后前往的地址
                .usernameParameter("loginAcct") // 账号的请求参数名称
                .passwordParameter("userPswd") // 密码的请求参数名称
                .and()
                .logout() // 退出登录
                .logoutUrl("/security/do/logout.html") // 指定退出登录地址
                .logoutSuccessUrl("/admin/to/login/page.html") // 指定退出后前往的地址
                ;
    }
}

