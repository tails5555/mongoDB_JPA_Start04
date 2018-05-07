# mongoDB_JPA_Start04
Spring Data MongoDB + JSP + WebSocket(SockJS EchoHandler) 간단한 채팅 메시징 처리 구현

## Issues
- JWT Token을 기반으로 사용자에게 Authentication을 할 수 있는 기능을 연습합니다.
- 이번에는 EchoHandler 클래스를 이용해서 간략한 메시징을 할 수 있는 연습을 진행합니다.
- ReactJS를 기반으로 REST API 통신을 하여 데이터를 받아 오는 연습을 진행합니다.

## Study Docs
스터디 자료는 현재 프로젝트의 `src > doc` 파일에 PDF 파일로 제공을 하였습니다.
 
스터디 자료는 향시에 수정이 될 수 있으니 이 점 참고하시길 바라겠습니다.

[스터디 자료 참고하기](https://github.com/tails5555/mongoDB_JPA_Start04/blob/master/src/doc/MongoDB%2BSpringJPA_05_EchoHandler_And_ReactJS_%EC%A0%91%EB%AA%A9.pdf)

## Maven pom.xml
`pom.xml` 를 기반으로 Maven Dependency를 구성하여 Update Maven은 필수입니다

```
<dependencies>
	<!-- 1. Spring Web MVC Starter -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<!-- 2. Spring WebSocket Starter -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-websocket</artifactId>
	</dependency>
	<!-- 3. Spring Data MongoDB Starter -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-mongodb</artifactId>
	</dependency>
	<!-- 4. Tomcat Starter -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-tomcat</artifactId>
		<scope>provided</scope>
	</dependency>
	<!-- 5. Spring Test Starter -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
	<!-- 6. Lombok Project -->
	<!-- Lombok은 각 인스턴스들에 대해서 getter, setter, toString, equals, hashCode 등의 구현을 자동으로 해 주는 프로젝트이다. -->
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.16.20</version>
	</dependency>
	<!-- 7. Java JSON Web Token(JWT) Library -->
	<dependency>
		<groupId>io.jsonwebtoken</groupId>
		<artifactId>jjwt</artifactId>
		<version>0.6.0</version>
	</dependency>
</dependencies>
```

## application.properties 설정
- src > main > resources > application.properties에 현존하는 설정을 아래와 같은 방식으로 작성해서 이용하시면 됩니다.

```
spring.data.mongodb.host=[호스트 입력. localhost는 127.0.0.1입니다.]
spring.data.mongodb.port=[포트 번호 입력. 대부분 27017를 적용하지만, 클러스터링에 따라 27018, 27019 등을 쓸 수도 있습니다.]
spring.data.mongodb.database=[데이터베이스 입력]
spring.data.mongodb.username=[사용자 이름 입력]
spring.data.mongodb.password=[비밀번호 입력]
```

## Screenshot
![example05_login01](/src/doc/example05_login01.png "example05_login01")

React Application에서 로그인을 하는 장면.

![example05_login02](/src/doc/example05_login02.png "example05_login02")

React Application에서 로그인을 진행하고 난 후에 Redux Action에서 로그인 성공을 알리는 장면.

![example05_result01](/src/doc/example05_result01.png "example05_result01")

현재 사용자와 타 사용자 끼리 채팅을 하는 장면[1]

![example05_result02](/src/doc/example05_result02.png "example05_result02")

현재 사용자와 타 사용자 끼리 채팅을 하는 장면[2]

![example05_result03](/src/doc/example05_result03.png "example05_result03")

현재 사용자와 타 사용자 끼리 채팅한 내용들이 MongoDB에 저장되는 장면입니다.

## Author
- [강인성](https://github.com/tails5555)