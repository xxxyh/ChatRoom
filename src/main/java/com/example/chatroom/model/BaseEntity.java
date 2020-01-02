package com.example.chatroom.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.chatroom.enums.Status;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "status", fill = FieldFill.INSERT)
    private Status status;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableLogic
    private int deleted;
}
