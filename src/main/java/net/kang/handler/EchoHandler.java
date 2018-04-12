package net.kang.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.kang.domain.OutputMessage;
import net.kang.model.Message;
import net.kang.service.OutputMessageService;

@Component
public class EchoHandler extends TextWebSocketHandler{
	private static Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	@Autowired OutputMessageService outputMessageService;

	private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

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
		boolean isWrited=false;
		for(WebSocketSession ws : sessions) {
			String messageResult = message.getPayload();
			if(messageResult.equals("chattingList")) {
				if(session.getId().equals(ws.getId())) { // 새로고침은 자신만 할 수 있도록 설정을 하였다.
					String messageJSON = jsonStringFromObject(outputMessageService.findAll());
					ws.sendMessage(new TextMessage(String.format("%s", messageJSON)));
				}
			} else if(messageResult.contains("addMessage")) { // 메시지를 전송하는 사람의 시점을 고려한다.
				if(session.getId().equals(ws.getId())) { // 메시지를 전송하는 사람이 서로 같은 경우에는...
					String[] messageToken = messageResult.split("\n");
					Message msg = new Message();
					msg.setText(messageToken[1]);
					msg.setFrom(messageToken[2]);
					outputMessageService.insert(msg, "코딩이야기"); // 메시지를 1개만 추가를 하도록 형성한다.
					isWrited=true;
				}
				if(isWrited) break;
			}
		}
		if(isWrited) {
			for(WebSocketSession ws2 : sessions) {
				List<OutputMessage> tmpList=new ArrayList<OutputMessage>();
				tmpList.add(outputMessageService.findTopByOrderByCurrentTimeDesc());
				String messageJSON = jsonStringFromObject(tmpList);
				ws2.sendMessage(new TextMessage(String.format("%s", messageJSON)));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		sessions.remove(session);
		System.out.println(" => disconnected by session : "+session.getId());
		// logger.info("disconnected by session : {0}", session.getId());
	}

}
