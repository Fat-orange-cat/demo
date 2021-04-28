package com.example.test.demo.bean;

import lombok.Data;

/**
 * <p>
 * <code>MessageBody</code>
 * </p>
 * websocketTest
 * @author lbd
 * @version 1.0
 * @date 2021-04-28
 */
@Data
public class MessageBody {
    //发送者
    private String from;
    //目标用户
    private String targetUser;

    //消息内容
    private String content;
    //广播地址
    private String destination;
}
