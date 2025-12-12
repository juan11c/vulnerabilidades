package com.vulscanner.scanner_api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vulscanner.scanner_api.exception.PasswordInvalidaException;
import com.vulscanner.scanner_api.exception.UsuarioNoEncontradoException;
import com.vulscanner.scanner_api.model.Usuario;
import com.vulscanner.scanner_api.repository.UsuarioRepository;
import com.vulscanner.scanner_api.security.JwtTokenProvider;

@Service
public class AuthService {
	private final UsuarioRepository usuarioRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UsuarioRepository usuarioRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	public String authenticate(String email, String rawPassword) {		
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no registrado"));

		if (!passwordEncoder.matches(rawPassword, usuario.getPassword())) {
			System.out.print("Entro if password encoder");
			throw new PasswordInvalidaException("Contraseña incorrecta");
			//throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
		}

		return jwtTokenProvider.generateToken(email);
	}
}