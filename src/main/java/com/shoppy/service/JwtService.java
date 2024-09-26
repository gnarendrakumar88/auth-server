package com.shoppy.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.shoppy.model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	@Autowired
	private MyUserDetailsService userService;
	
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
    private static final String SECRET_KEY = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
    	
    	Claims claims = Jwts.claims();
    	Users user = userService.getUser(username);
    	claims.put("name",user.getName());
    	claims.put("role","ROLE_"+user.getRole());
    	
    	return Jwts.builder()
    			.setClaims(claims)
    			.setSubject(username)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

	public String extractUserName(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
	
	private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token).getBody();
    }
}
