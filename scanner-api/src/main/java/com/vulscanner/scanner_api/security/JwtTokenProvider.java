package com.vulscanner.scanner_api.security;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vulscanner.scanner_api.exception.TokenInvalidException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey; // ahora se carga desde application.properties o variable de entorno

    private Key key;

    @PostConstruct
    public void init() {
        // Inicializa la clave después de cargar la propiedad
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(3600); // 1 hora de validez

        return Jwts.builder()
                   .setSubject(email)
                   .setIssuedAt(Date.from(now))
                   .setExpiration(Date.from(expiry))
                   .signWith(key)
                   .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (Exception e) {
            throw new TokenInvalidException("No se pudo extraer el usuario del token: " + e.getMessage());
        }
    }

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
