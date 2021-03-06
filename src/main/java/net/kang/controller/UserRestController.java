package net.kang.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.kang.domain.User;
import net.kang.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserRestController {
	@Autowired UserService userService;

	@GetMapping("findByToken/{token}") // 현재 User의 Token을 받아서 유효성을 확인하기 위한 함수로 작성.
	public ResponseEntity<?> tokenChecking(@PathVariable("token") String token, HttpServletResponse response) throws UnsupportedEncodingException, ServletException{
		User user = userService.findByToken(token);
		if(user.equals(new User())) {
			return new ResponseEntity<ServletException>(new ServletException("토큰이 무효합니다."), HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
}
