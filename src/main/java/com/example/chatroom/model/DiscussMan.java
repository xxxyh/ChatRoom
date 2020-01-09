package com.example.chatroom.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天室参与人表
 * </p>
 *
 * @author fangke
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("d_discuss_man")
public class DiscussMan extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 聊天室id
     */
    private Integer discussId;

    /**
     * 用户id
     */
    private Integer memberId;

    /**
     * 1-创建人 2-管理员 3-普通成员
     */
    private Integer manType;

    /**
     * 提醒 1-提醒 2-不提醒
     */
    private Integer remind;



}
