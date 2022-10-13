package com.geopagos.toumament.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtProvider jwtTokenProvider;

	public JwtTokenFilter(JwtProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = this.jwtTokenProvider.resolveToken(request);
		try {
			if (token != null && this.jwtTokenProvider.validateToken(token)) {
				Authentication auth = this.jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (RuntimeException e) {
			SecurityContextHolder.clearContext();
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}

}
