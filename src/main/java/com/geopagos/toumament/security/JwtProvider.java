package com.geopagos.toumament.security;

import com.geopagos.toumament.dto.RoleDTO;
import com.geopagos.toumament.dto.UserPrincipalDTO;
import com.geopagos.toumament.errorhandler.GeopagosToumamentGenericClientException;
import com.geopagos.toumament.service.security.GeopagosToumamentDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements Serializable {

	private static final long serialVersionUID = 1L;
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private Long validityMilliseconds;

	@Autowired
	private GeopagosToumamentDetailService pignoraticioDetailService;

	public String createToken(Object object, List<RoleDTO> roles) {
		Claims claims = Jwts.claims().setSubject(object.toString());
		claims.put("auth", roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList()));
		Date date = new Date();
		Date validity = new Date(date.getTime() + validityMilliseconds*100);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(date)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new GeopagosToumamentGenericClientException("Expired or invalid JWT token", HttpStatus.BAD_REQUEST);
		}
	}

	public Authentication getAuthentication(String token) {
		UserPrincipalDTO userDetails = (UserPrincipalDTO) pignoraticioDetailService
				.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
