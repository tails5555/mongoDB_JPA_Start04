package net.kang.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document(collection="user")
public class User { // User에서 아이디와 비밀번호를 새로 추가하였다.
	@Id
	String id;
	String userId;

	@JsonIgnore
	String password;

	String name;
}
