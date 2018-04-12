package net.kang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import net.kang.handler.EchoHandler;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{ // 이번에는 WebSocket을 그대로 이용하되 EchoHandler를 이용해서 할 수 있도록 작성하였다.
	@Autowired EchoHandler echoHandler; // 이는 EchoHandler에 대한 Bean을 따로 Singleton을 이용하여 반환하지 않고 @Autowired를 이용해서 설정을 한다.

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(echoHandler, "/echo").setAllowedOrigins("*").withSockJS(); // WebSocket 중에서 SockJS를 사용할 수 있도록 설정을 하는 문장.
	}
}
