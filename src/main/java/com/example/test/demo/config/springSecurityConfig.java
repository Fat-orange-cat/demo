package com.example.test.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 认证服务配置类
 */
@Configuration
public class springSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    //配置数据源
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * rememberMe() 用于实现记住我功能。
         * tokenRepository() 设置数据访问层。
         * userDetailsService() 设置 userDetailsService。
         * tokenValiditySeconds() 设置过期时间。
         * rememberMeParameter() 自定义参数名，默认为 remember-me
         */
        http.rememberMe().
                tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60);
        /**
         * csrf() 表示开启 csrf 防护。
         * disable() 表示关闭 csrf 防护。
         */
        http.csrf().disable();

        /**
         * formLogin() 用于自定义表单登录。
         * loginPage() 用于自定义登录页面。
         * defaultSuccessUrl() 登录成功后 跳转的路径。
         * loginProcessingUrl() 表单提交的 action 地址（默认为 /login，修改后，对应的表单 action 也要修改），由系统提供 UsernamePasswordAuthenticationFilter 过滤器拦截并处理。
         * usernameParameter() 用于自定义表单提交的用户参数名，默认为 username，修改后，对应的表单参数也要修改。
         * passwordParameter() 用于自定义表单提交的用户密码名，默认为 password，修改后，对应的表单参数也要修改。
         * failureForwardUrl() 用于自定义表单提交失败后 重定向地址，可用于前后端分离中，指向某个 controller，注意使用 POST 处理。
         */
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/success.html")
                //.usernameParameter("name")
                //.passwordParameter("pwd")
                .failureForwardUrl("/test/error");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html");

        /**
         * authorizeRequests()  用于 开启认证，基于 HttpServletRequest 对 url 进行身份控制并授权访问。
         * antMatchers() 用于匹配 url。
         * permitAll() 用于允许任何人访问该 url。
         * hasAuthority() 用于指定 具有某种权限的 人才能访问 url。
         * hasAnyAuthority() 用于指定 多个权限 进行访问，多个权限间使用逗号分隔。
         *
         * hasRole() 写法与 hasAuthority() 类似，但是其会在 角色前 拼接上 ROLE_，使用时需要注意。
         * hasAnyRole() 写法与 hasAnyAuthority() 类似，同样会在 角色前 拼接上 ROLE_。
         *
         * 使用时 hasAuthority()、hasAnyAuthority() 或者 hasAnyRole()、hasAnyAuthority() 任选一对即可，同时使用四种可能会出现问题。
         */
        http.authorizeRequests()
                .antMatchers("/test/hello").hasAuthority("user")
                //.antMatchers("/test/hello").hasAnyRole("USER,GOD")
                //.antMatchers("/test/hello").hasRole("GOD")
                .antMatchers("/test/hello").hasAnyAuthority("user,admin")
                .antMatchers("/login", "/test/error").permitAll();

        /**
         * 自定义 403 页面
         */
        http.exceptionHandling().accessDeniedPage("/403.html");
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//关闭csrf防护
        *//**
         * formLogin() 用于自定义表单登录。
         * loginPage() 用于自定义登录页面。
         * defaultSuccessUrl() 登录成功后 跳转的路径。
         * loginProcessingUrl() 表单提交的 action 地址（默认为 /login，修改后，对应的表单 action 也要修改），由系统提供 UsernamePasswordAuthenticationFilter 过滤器拦截并处理。
         * usernameParameter() 用于自定义表单提交的用户参数名，默认为 username，修改后，对应的表单参数也要修改。
         * passwordParameter() 用于自定义表单提交的用户密码名，默认为 password，修改后，对应的表单参数也要修改。
         * failureForwardUrl() 用于自定义表单提交失败后 重定向地址，可用于前后端分离中，指向某个 controller，注意使用 POST 处理。
         *//*
        http.formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/test/hello")
                .failureForwardUrl("/test/error");
        *//**
         * authorizeRequests()  用于 开启认证，基于 HttpServletRequest 对 url 进行身份控制并授权访问。
         * antMatchers() 用于匹配 url。
         * permitAll() 用于允许任何人访问该 url。
         * hasAuthority() 用于指定 具有某种权限的 人才能访问 url。
         * hasAnyAuthority() 用于指定 多个权限 进行访问，多个权限间使用逗号分隔。
         *
         * hasRole() 写法与 hasAuthority() 类似，但是其会在 角色前 拼接上 ROLE_，使用时需要注意。
         * hasAnyRole() 写法与 hasAnyAuthority() 类似，同样会在 角色前 拼接上 ROLE_。
         *
         * 使用时 hasAuthority()、hasAnyAuthority() 或者 hasAnyRole()、hasAnyAuthority() 任选一对即可，同时使用四种可能会出现问题。
         *//*
        http.authorizeRequests()
                .antMatchers("/test/hello")
                .hasAuthority("user")
                .antMatchers("/test/hello","/test/error");

        http.exceptionHandling().accessDeniedPage("/403.html");
    }*/
}
