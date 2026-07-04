package com.eventhub.auth.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventhub.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Autowired
	private final JwtProperties jwtProperties;
	
	@Autowired
	private final SecretKey secretKey;
	
	private static final String ROLE_CLAIM = "role";
	
	public JwtService(JwtProperties jwtProperties) {
		
		this.jwtProperties = jwtProperties;
		this.secretKey = Keys.hmacShaKeyFor(
				jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(User user) {
		
		Map<String,Object> extraClaims = new HashMap<>();
		extraClaims.put(ROLE_CLAIM, user.getRole().name());
		
		return generateToken(extraClaims, user);
	}
	
	public String generateToken(Map<String,Object> extraClaims,User user) {
		
		return buildToken(extraClaims,user);
	}
	
	public <T> T extractClaim(String token,Function<Claims, T> claimsResolver) {
		
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractSubject(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean isTokenValid(String token,User user) {
		
		String subject = extractSubject(token);
		return subject.equals(String.valueOf(user.getId())) && !isTokenExpired(token);
	}
	
	private String buildToken(Map<String,Object> extraClaims,User user) {
		
		Instant now = Instant.now();
		Instant expiration = now.plus(jwtProperties.expiration());
		
		Date issuedAt = Date.from(now);
		Date expiresAt = Date.from(expiration);
		 	
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(String.valueOf(user.getId()))
				.setIssuer(jwtProperties.issuer())
				.setIssuedAt(issuedAt)
				.setExpiration(expiresAt)
				.signWith(secretKey,SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				   .setSigningKey(secretKey)
				   .build()
				   .parseClaimsJws(token)
				   .getBody();
	}
}
