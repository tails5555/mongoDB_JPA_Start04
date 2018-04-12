package net.kang.model;

import lombok.Data;

@Data
public class Message { // 채팅 내용 Form
	String from;
	String text;
}
