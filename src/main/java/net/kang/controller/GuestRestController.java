package net.kang.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.kang.domain.User;
import net.kang.model.LoginForm;
import net.kang.service.TokenAuthenticationService;
import net.kang.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("guest")
public class GuestRestController {
	@Autowired UserService userService;
	@Autowired TokenAuthenticationService tokenAuthenticationService;
	@PostMapping("login")
	public ResponseEntity<?> login(HttpServletResponse response, @RequestBody LoginForm loginForm) throws IOException{
		User loginUser=userService.login(loginForm.getUserId(), loginForm.getPassword());
		if(loginUser==null) return new ResponseEntity<ServletException>(new ServletException("존재하지 않은 사용자입니다."), HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<String>(tokenAuthenticationService.addAuthentication(response, loginUser), HttpStatus.OK);
	}
}
