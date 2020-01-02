package com.example.chatroom.config.jwt.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class UserAuthenticationFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream outputStream = httpServletResponse.getOutputStream();//获取OutputStream输出流
        httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
        if(e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
            outputStream.write("邮箱或者密码错误".getBytes(StandardCharsets.UTF_8));
        }else if (e instanceof LockedException){
            outputStream.write("账户已被锁定".getBytes(StandardCharsets.UTF_8));
        }else{
            outputStream.write("未知错误".getBytes(StandardCharsets.UTF_8));
        }
    }
}