package com.vulscanner.scanner_api.security;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.vulscanner.scanner_api.exception.TokenInvalidException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private final String secretKey = "clave-secreta-super-segura-clave-secreta-super-segura"; // mínimo 256 bits
	private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

	public String generateToken(String email) {
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(3600); // 1 hora

		return Jwts.builder().setSubject(email).setIssuedAt(Date.from(now)).setExpiration(Date.from(expiry))
				.signWith(key).compact();

	}

//	public String getUsernameFromToken(String token) {
//		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
//	}
	
	public String getUsernameFromToken(String token) {
		try {
		    return Jwts.parserBuilder()
			        .setSigningKey(key)
			        .build()
			        .parseClaimsJws(token)
			        .getBody()
			        .getSubject();
		} catch (Exception e){
			throw new TokenInvalidException("No se pudo extraer el usuario del token: " + e.getMessage());
		}
	}

//	public boolean validateToken(String token) {
//		try {
//			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//			return true;
//		} catch (Exception e) {
//			System.out.println("Error al validar token: " + e.getClass().getSimpleName() + " - " + e.getMessage());
//			return false;
//		}
//	}
	
	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token);
	        return true;
	    } catch (Exception e) {
	    	throw new TokenInvalidException("Token inválido: " + e.getMessage());
	    }
	}
}
