package com.vulscanner.scanner_api.security;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter -> Garantiza que el filtro se ejecute una vez por request

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization"); // getHeader -> Extrae el token del encabezado HTTP
        System.out.println("Filtro JWT ejecutado para ruta: " + request.getRequestURI());

        if (header != null && header.startsWith("Bearer ")) { // startsWith("Bearer ") -> Verifica que el token tenga el formato correcto
            String token = header.substring(7); // Quita el prefijo "Bearer " para obtener solo el token
            System.out.println("Filtro JWT ejecutado para token: " + token);

            if (jwtTokenProvider.validateToken(token)) { // validateToken -> Verifica que el token sea válido y no haya expirado
                String email = jwtTokenProvider.getUsernameFromToken(token); // getUsernameFromToken(token) -> Extrae el email (o username) del token

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList()); // Crea una autenticación temporal para este usuario

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // setDetails -> Agrega detalles del request (IP, headers, etc.)

                SecurityContextHolder.getContext().setAuthentication(authentication); // Registra al usuario como autenticado en el contexto de Spring
                System.out.println("Token recibido: " + token);
                System.out.println("Email extraído: " + email);
                System.out.println("Autenticación registrada");
            }
        }

        filterChain.doFilter(request, response); // Continúa con el flujo normal del reques
    }
}