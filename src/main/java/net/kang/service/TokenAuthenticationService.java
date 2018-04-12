package net.kang.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.kang.domain.User;
import net.kang.repository.UserRepository;

@Service
public class TokenAuthenticationService {
	private long EXPIRATIONTIME=1000*60*60; // 1시간
	private String secret="secretkey"; // ScrectKey에 대해 임시로 secretkey로 설정.
	@Autowired UserRepository userRepository;
	public String addAuthentication(HttpServletResponse response, User user) throws IOException { // 로그인을 진행하고 난 후에 JWT Token을 반환한다.
		Claims claimList=Jwts.claims();
		claimList.put("userId", user.getUserId());
		claimList.put("name", user.getName());
		String jwt=Jwts.builder()
				.setSubject(user.getId())
				.setClaims(claimList)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
	             .signWith(SignatureAlgorithm.HS256, secret)
	             .compact();
		return jwt;
	}
}