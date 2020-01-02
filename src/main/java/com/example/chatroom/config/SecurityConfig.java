package com.example.chatroom.config;

import com.example.chatroom.config.jwt.EmailAuthenticationProvider;
import com.example.chatroom.config.jwt.JsonLoginConfigurer;
import com.example.chatroom.config.jwt.JwtAuthenticationProvider;
import com.example.chatroom.config.jwt.JwtLoginConfigurer;
import com.example.chatroom.config.jwt.JwtUserService;
import com.example.chatroom.config.jwt.handler.JwtRefreshSuccessHandler;
import com.example.chatroom.config.jwt.handler.UserAuthenticationSuccessHandler;
import com.example.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtUserService jwtUserService;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.authorizeRequests().antMatchers().hasRole("USER")
                .anyRequest().authenticated()
                .and().csrf().disable() // CRSF禁用，因为不使用session
                .sessionManagement().disable() // 禁用session
                .formLogin().disable() // 禁用form登录.and() //拦截OPTONS请求，直接返回header
                // 添加登录filter
                .apply(new JsonLoginConfigurer<>(userService)).loginSuccessHandler(userAuthenticationSuccessHandler()).and()
                // 添加token的filter
                .apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jwtRefreshSuccessHandler())
                .permissiveRequestUrls("/logout").and()
                // 使用默认的logoutFilter
                .logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()); // logout成功后返回200
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider()).authenticationProvider(emailAuthenticationProvider());
        auth.userDetailsService(jwtUserService).passwordEncoder(passwordEncoder());
    }

    /**
     * 不登录请求接口会返回403，token失效会返回401，统一都返回401重新登录
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("请登录");
            response.setStatus(401);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtUserService);
    }

    @Bean
    protected AuthenticationProvider daoAuthenticationProvider() {
        // 这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(jwtUserService);
        return daoProvider;
    }

    @Bean
    protected AuthenticationProvider emailAuthenticationProvider() {
        EmailAuthenticationProvider emailAuthenticationProvider = new EmailAuthenticationProvider();
        emailAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        emailAuthenticationProvider.setUserDetailsService(jwtUserService);
        return emailAuthenticationProvider;
    }

    @Bean
    protected UserAuthenticationSuccessHandler userAuthenticationSuccessHandler() {
        return new UserAuthenticationSuccessHandler(jwtUserService);
    }

    @Bean
    protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
        return new JwtRefreshSuccessHandler(jwtUserService);
    }
}
