package net.kang.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
@Document(collection="outputmessage")
public class OutputMessage {
	@Id
	String id;

	@DBRef(db="mesenger_example_react", lazy=false)
	User from;

	String message;
	String topic;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	Date currentTime;
}
