package com.zx.webSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 16:15
 * @note
 */
@ServerEndpoint("/websocket/{uid}")
@Component
public class webSocketImpl {
    /**
     * 用户连接方法
     */
    @OnOpen
    public void onOpen(){

    }

    /**
     * 用户发送消息方法
     */
    @OnMessage
    public void onMessage(String message, Session session){

    }

    @OnClose
    public void onClose(){

    }

    @OnError
    public void onError(Session session,Throwable throwable){

    }


}
