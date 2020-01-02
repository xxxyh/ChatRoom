package com.example.chatroom.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.chatroom.enums.Status;
import com.example.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JwtUserService implements UserDetailsService {
    public static final String SALT = "123456ef";

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) {
        com.example.chatroom.model.User user = userService.findUserByName(s, null);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        Status status = user.getStatus();
        boolean locked = (status != Status.LOCKED);
        return new User(s, user.getPassword(), true, true, true, locked, getGrantedAuthorities(user));
    }

    public String saveUserLoginInfo(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(SALT);
        // 设置1小时后过期
        Date date = new Date(System.currentTimeMillis() + 3600L);
        return JWT.create().withSubject(user.getUsername()).withExpiresAt(date).withIssuedAt(new Date())
            .sign(algorithm);
    }

    public UserDetails getUserLoginInfo(String username) {
        UserDetails user = loadUserByUsername(username);
        // 将salt放到password字段返回
        return User.builder().username(user.getUsername()).password(SALT).authorities(user.getAuthorities()).build();
    }

    private List<GrantedAuthority> getGrantedAuthorities(com.example.chatroom.model.User user) {
        //暂时不加用户类型
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    public com.example.chatroom.model.User getUserByName(String name) {
        return userService.findUserByName(name);
    }
}
