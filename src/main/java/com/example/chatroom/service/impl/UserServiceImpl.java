package com.example.chatroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.chatroom.mapper.UserMapper;
import com.example.chatroom.model.User;
import com.example.chatroom.service.UserService;
import com.example.chatroom.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public int createUser(User user){
        Assert.notNull(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insert(user);
    }

    @Override
    public User findUserById(Integer id) {
        Assert.notNull(id);

        return userMapper.selectById(id);
    }

    @Override
    public User findUserByName(String name, User user) {
        Assert.notNull(name);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", name);
        User result = userMapper.selectOne(wrapper);
        if(result == null){
            return  user;
        }
        return result;
    }

    @Override
    public User findUserByName(String name) {
        Assert.notNull(name);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", name);
        User user = userMapper.selectOne(wrapper);
        Assert.notNull(user);
        return user;
    }
}
