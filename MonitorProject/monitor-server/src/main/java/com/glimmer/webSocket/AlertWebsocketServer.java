package com.glimmer.webSocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/*
AlertWebsocketServer 使用websocket来实现报警信息的实时推送，这里是使用spring-boot-starter-websocket中集成的webSocket
通过注入AlertWebsocketServer类从AlertTransactionServiceImpl中获取报警信息，并且这里保持和每个客户端的连接，然后将报警信息推送给每个客户端
*/
@Component
@ServerEndpoint("/ws/alert")
public class AlertWebsocketServer {

}
