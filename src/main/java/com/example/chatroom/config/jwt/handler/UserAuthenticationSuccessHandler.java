package com.example.chatroom.config.jwt.handler;

import com.example.chatroom.config.jwt.JwtUserService;
import com.example.chatroom.model.Result;
import com.example.chatroom.model.User;
import com.example.chatroom.utils.JsonUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtUserService jwtUserService;
    public UserAuthenticationSuccessHandler(JwtUserService jwtUserService){
        this.jwtUserService = jwtUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //生成token，并把token加密相关信息缓存，具体请看实现类
        String token = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
        httpServletResponse.setHeader("Authorization", token);
        OutputStream outputStream = httpServletResponse.getOutputStream();//获取OutputStream输出流
        httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码

        User user = jwtUserService.getUserByName(((UserDetails)authentication.getPrincipal()).getUsername());
        user.setPassword(null);
        String result = JsonUtils.write(Result.ok(user));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));//使用OutputStream流向客户端输出字节数组
    }
}
