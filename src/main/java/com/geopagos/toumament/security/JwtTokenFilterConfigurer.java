package com.geopagos.toumament.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JwtProvider jwtTokenProvider;

	public JwtTokenFilterConfigurer(JwtProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		JwtTokenFilter tokenFilter = new JwtTokenFilter(this.jwtTokenProvider);
		builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
