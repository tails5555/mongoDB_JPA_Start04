package net.kang.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EchoHandler extends TextWebSocketHandler{
	private static Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		sessions.add(session);
		System.out.println(" => connected by session : "+session.getId());
		// logger.info("disconnected by session : {0}", session.getId());
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		System.out.println(" => "+session.getId()+" send message -> "+message.getPayload());
		// logger.info("{0} send message -> {1}", session.getId(), message.getPayload());
		for(WebSocketSession ws : sessions) {
			ws.sendMessage(new TextMessage(String.format("echo : %s", message.getPayload())));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		sessions.remove(session);
		System.out.println(" => disconnected by session : "+session.getId());
		// logger.info("disconnected by session : {0}", session.getId());
	}

}
