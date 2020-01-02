package com.example.chatroom.config.jwt.filter;

import com.example.chatroom.model.User;
import com.example.chatroom.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    public UsernamePasswordAuthenticationFilter(UserService userService) {
        // 拦截url为 "/login" 的POST请求
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) {
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");

        if (email == null) {
            email = "";
        }
        if (password == null) {
            password = "";
        }
        email = email.trim();
        // 封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = null;
        try {
            authentication = this.getAuthenticationManager().authenticate(authRequest);
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (BadCredentialsException bc) {
            //暂时不锁账号
            //userService.incrUserLock(email);
            throw bc;
        } catch (LockedException le) {
            httpServletRequest.setAttribute("msg", "locked");
            throw le;
        } catch (AuthenticationException ae) {
            throw ae;
        }
        return authentication;
    }

}
