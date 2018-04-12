package net.kang.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kang.domain.OutputMessage;
import net.kang.domain.User;
import net.kang.model.Message;
import net.kang.repository.OutputMessageRepository;
import net.kang.repository.UserRepository;
@Service
public class OutputMessageService {
	@Autowired UserRepository userRepository;
	@Autowired OutputMessageRepository outputMessageRepository;
	public List<OutputMessage> findAll(){ // 모든 채팅 내용 반환
		return outputMessageRepository.findAll();
	}
	public boolean insert(Message message, String topic) { // 채팅 내용 추가
		OutputMessage newMessage=new OutputMessage();
		Optional<User> user=userRepository.findByUserId(message.getFrom());
		User realUser=user.orElse(new User());
		if(user.equals(new User())) return false;
		newMessage.setFrom(realUser);
		newMessage.setCurrentTime(new Date());
		newMessage.setTopic(topic);
		newMessage.setMessage(message.getText());
		outputMessageRepository.insert(newMessage);
		return true;
	}
	public OutputMessage findTopByOrderByCurrentTimeDesc() { // 채팅 내용 중에서 최신 내용들을 불러옴
		return outputMessageRepository.findTopByOrderByCurrentTimeDesc();
	}
}
