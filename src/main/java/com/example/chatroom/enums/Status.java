package com.example.chatroom.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum Status implements IEnum<Integer> {
    LOCKED(1, "已被锁"), NORMAL(2, "正常");

    Status(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private Integer value;
    private String desc;

    @Override
    public Integer getValue() {
        return this.value;
    }

}
