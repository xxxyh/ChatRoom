package com.example.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.chatroom.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
}
