package com.example.test.demo.controller;

import com.example.test.demo.bean.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * <p>
 * <code>WebSocketTestController</code>
 * </p>
 *
 * @description: WebSocketTest
 * @author: lbd
 * @version: 1.0
 * @date: 2021-04-28
 */
@Controller
public class WebSocketTestController {

    /*消息发送工具*/
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    /** 广播发送消息，将消息发送到指定的目标地址 */
    @MessageMapping("/testWebSocket")
    public void sendTopicMessage(MessageBody messageBody){
        // 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
        simpMessageSendingOperations.convertAndSend(messageBody.getDestination(),messageBody);
    }

    /**
     *
     * @author liubangdeng
     * @annotation:
     * @param principal
     * @param messageBody
     * @throws
     * @return void
     * @date 2021/4/29 8:43
     */
    @MessageMapping("testWebSocketByUser")
    public void sendUserMessage(Principal principal,MessageBody messageBody){
        messageBody.setFrom(principal.getName());//获取发送者
        simpMessageSendingOperations.convertAndSendToUser(messageBody.getTargetUser(),messageBody.getDestination(),messageBody);
    }
}
