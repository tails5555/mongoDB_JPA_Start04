package net.kang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import net.kang.domain.OutputMessage;
import net.kang.model.Message;
import net.kang.service.OutputMessageService;

@RestController
@CrossOrigin
public class ChatSocketController {
	@Autowired OutputMessageService outputMessageService;

	@MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public OutputMessage send(@DestinationVariable("topic") String topic, Message message) throws Exception{
		outputMessageService.insert(message, topic);
		return outputMessageService.findTopByOrderByCurrentTimeDesc();
	}

	@MessageMapping("/chat/list")
	@SendTo("/topic/message/refresh")
	public List<OutputMessage> findAll(){
		return outputMessageService.findAll();
	}
}
