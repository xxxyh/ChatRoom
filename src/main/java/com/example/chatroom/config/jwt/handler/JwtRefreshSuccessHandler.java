package com.example.chatroom.config.jwt.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.chatroom.config.jwt.JwtAuthenticationToken;
import com.example.chatroom.config.jwt.JwtUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    //刷新间隔5分钟
    private static final int TOKEN_REFRESH_INTERVAL = 1200;

    private JwtUserService jwtUserService;

    public JwtRefreshSuccessHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication){
        DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
        boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
        if(shouldRefresh) {
            String newToken = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
            response.setHeader("Authorization", newToken);
        }
    }

    protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }

}
