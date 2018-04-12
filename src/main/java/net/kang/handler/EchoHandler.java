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

@Component // 이를 @Autowired를 할 수 있도록 하기 위해 Component 어노테이션으로 추가한다.
public class EchoHandler extends TextWebSocketHandler{
	private static Logger logger = LoggerFactory.getLogger(EchoHandler.class); // Logger에 대해서도 활용을 연습하기 위해 주석 처리를 이용해서 설정을 해 뒀다.
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>(); // 현재 Messenger에 접속된 Session 목록들을 저장한다.
	@Autowired OutputMessageService outputMessageService; // EchoHandler에서 MongoDB와 접목하기 위해 Service 클래스 생성

	private String jsonStringFromObject(Object object) throws JsonProcessingException { // 이는 Java에서 쓰인 모든 객체들에 대해서 String JSON으로 반환한다.
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{ // WebSocket에 접속한 client session에 대해 등록을 한다.
		sessions.add(session);
		System.out.println(" => connected by session : "+session.getId());
		// logger.info("disconnected by session : {0}", session.getId());
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{ // handleTextMessage 함수에서는 사용자가 보낸 message에 대해서 받아와서 데이터베이스에 저장을 할 수 있는 문장을 작성하였다.
		System.out.println(" => "+session.getId()+" send message -> "+message.getPayload());
		// logger.info("{0} send message -> {1}", session.getId(), message.getPayload());
		boolean isWrited=false; // 이는 사용자가 채팅 내용을 입력하는 경우에 이를 true로 설정을 하여 모든 사람들에게 새로운 인원이 입력한 채팅 내용을 보내준다.
		for(WebSocketSession ws : sessions) { // 각 WebSocket에 접속한 session들에 대해서 각각 순회를 하여 Message를 확인하도록 한다.
			String messageResult = message.getPayload();
			if(messageResult.equals("chattingList")) {
				if(session.getId().equals(ws.getId())) { // 새로고침은 자신만 할 수 있도록 설정을 하였다.
					String messageJSON = jsonStringFromObject(outputMessageService.findAll());
					ws.sendMessage(new TextMessage(String.format("%s", messageJSON))); // 현재 Database에 있는 메신저 목록들을 불러온다.
				}
			} else if(messageResult.contains("addMessage")) { // 메시지를 전송하는 사람의 시점을 고려한다.
				if(session.getId().equals(ws.getId())) { // 메시지를 전송하는 사람이 서로 같은 경우에는...
					String[] messageToken = messageResult.split("\n"); // 메시지에서는 각 요소들에 대해 임시적으로 개행 문자를 통해 구분을 하도록 설정을 하여 각 요소들을 추가한다.
					Message msg = new Message();
					msg.setText(messageToken[1]);
					msg.setFrom(messageToken[2]);
					outputMessageService.insert(msg, messageToken[3]); // 메시지를 1개만 추가를 하도록 형성한다.
					isWrited=true;
				}
				if(isWrited) break;
			}
		}
		if(isWrited) { // 현재 Session 목록들에서 사용자가 글을 추가한 경우에 각 Message에 대해 JSON으로 반환을 해 준다.
			for(WebSocketSession ws2 : sessions) {
				List<OutputMessage> tmpList=new ArrayList<OutputMessage>();
				tmpList.add(outputMessageService.findTopByOrderByCurrentTimeDesc());
				String messageJSON = jsonStringFromObject(tmpList);
				ws2.sendMessage(new TextMessage(String.format("%s", messageJSON)));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{ // 각 사용자들 중에서 현재 채팅을 종료하거나 로그아웃을 한다면 이를 선언하여 session 목록에서 제거한다.
		sessions.remove(session);
		System.out.println(" => disconnected by session : "+session.getId());
		// logger.info("disconnected by session : {0}", session.getId());
	}

}
