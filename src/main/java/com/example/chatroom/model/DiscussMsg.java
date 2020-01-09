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
 * 聊天室消息表
 * </p>
 *
 * @author fangke
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("d_discuss_msg")
public class DiscussMsg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 聊天室ID
     */
    private Integer discussId;

    /**
     * 消息发送人ID
     */
    private Integer userId;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息类型 0文本1图片2语音3视频4位置5文件
     */
    private Integer type;



}
