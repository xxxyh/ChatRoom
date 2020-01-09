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
 * 聊天室
 * </p>
 *
 * @author fangke
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("d_discuss")
public class Discuss implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 聊天室id
     */
    @TableId(value = "discuss_id", type = IdType.AUTO)
    private Integer discussId;

    /**
     * 聊天室名称
     */
    private String discussTitle;

    /**
     * 发起人id
     */
    private Integer userId;

    /**
     * 加入方式 1.自由加入 2.邀请加入
     */
    private Integer visibleType;

    /**
     * 话题状态 1-正常 2-删除
     */
    private Integer status;

    /**
     * 创建时间 
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 修改人
     */
    private Integer updateId;

    /**
     * 是否删除
     */
    private Integer deleted;


}
