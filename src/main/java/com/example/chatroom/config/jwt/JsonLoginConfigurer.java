package com.example.chatroom.config.jwt;

import com.example.chatroom.config.jwt.filter.UsernamePasswordAuthenticationFilter;
import com.example.chatroom.config.jwt.handler.UserAuthenticationFailHandler;
import com.example.chatroom.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;


public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
    extends AbstractHttpConfigurer<T, B> {

    private UsernamePasswordAuthenticationFilter authFilter;

    public JsonLoginConfigurer(UserService userService) {
        this.authFilter = new UsernamePasswordAuthenticationFilter(userService);
    }

    @Override
    public void configure(B http) {
        // 设置Filter使用的AuthenticationManager,这里取公共的即可
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 设置失败的Handler
        authFilter.setAuthenticationFailureHandler(new UserAuthenticationFailHandler());
        // 不将认证后的context放入session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        UsernamePasswordAuthenticationFilter filter = postProcess(authFilter);
        // 指定Filter的位置
        http.addFilterAfter(filter, LogoutFilter.class);
    }

    // 设置成功的Handler，这个handler定义成Bean，所以从外面set进来
    public JsonLoginConfigurer<T, B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler) {
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }

}