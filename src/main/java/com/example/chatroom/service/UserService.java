package com.example.chatroom.service;

import com.example.chatroom.model.User;

public interface UserService {
    int createUser(User user);
    User findUserById(Integer id);

    /**
     * 根据姓名查找user，可以找不到，找不到就返回默认值
     * @param name
     * @param user
     * @return
     */
    User findUserByName(String name, User user);

    /**
     * 根据姓名查找user，不允许找不到
     * @param name
     * @return
     */
    User findUserByName(String name);
}
