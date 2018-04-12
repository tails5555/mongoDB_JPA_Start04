package net.kang.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean{ // JWT에 대해서 Filter를 철저하게 할 수 있도록 하기 위해 Bean을 형성함.
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException{
		final HttpServletRequest request=(HttpServletRequest) req;
		final HttpServletResponse response=(HttpServletResponse) res;
		final String authHeader=request.getHeader("authorization"); // JWT 토큰을 받아올 때 확인하기 위한 함수
		if("OPTIONS".equals(request.getMethod())) { // OPTIONS 관련 RequestMethod를 확인한다.
			response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
		}else { // JWT 토큰은 Bearer 뒤에 붙어야 확인이 가능한데 불가능하면 ServletException을 통해 예외처리한다.
			if(authHeader==null || !authHeader.startsWith("Bearer ")) {
				throw new ServletException("Missing or invalid Authorization header");
			}
			final String token = authHeader.substring(7); // Bearer를 없애기 위한 문장.
			try { // secretKey에 대해 확인을 진행하고 난 후에 REST API에서도 보안이 확인됨을 진행한다.
				final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
				request.setAttribute("claims", claims);
			} catch (final SignatureException e) { // JWT 예외 중 대표적으로 제한 시간이 지난다면 ServletException을 통해 예외처리한다.
				throw new ServletException("Invalid token");
			}
			chain.doFilter(req, res);
		}
	}
}