package com.example.chatroom.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.chatroom.enums.Status;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MetaHandler implements MetaObjectHandler {
    @Override public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.setFieldValByName("createDate", date, metaObject);
        this.setFieldValByName("updateDate", date, metaObject);
        this.setFieldValByName("deleted", 0, metaObject);
        this.setFieldValByName("status", Status.NORMAL, metaObject);
    }

    @Override public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", new Date(), metaObject);
    }
}
