package com.vulscanner.scanner_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vulscanner.scanner_api.dto.UsuarioDTO;
import com.vulscanner.scanner_api.mapper.UsuarioMapper;
import com.vulscanner.scanner_api.model.Usuario;
import com.vulscanner.scanner_api.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;

	
	@Autowired
    private PasswordEncoder passwordEncoder;

	
	public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioMapper = usuarioMapper;
	}
	
//	public Usuario registrarUsuario(UsuarioDTO request){
//		Usuario usuario = new Usuario();
//		usuario.setUsername(request.getUsername());
//		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
//		usuario.setName(request.getName());
//		usuario.setEmail(request.getEmail());
//		usuario.setFechaCreacion(LocalDateTime.now());
//		
//		return usuarioRepository.save(usuario);
//	}
	
	public Usuario registrarUsuario(UsuarioDTO request) {
		Usuario usuario = usuarioMapper.toEntity(request);
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		return usuarioRepository.save(usuario);
		/*
		 * ¿Qué hace usuarioRepository.save(usuario)? Este método: - Guarda el objeto en
		 * la base de datos - Y devuelve una nueva instancia del mismo objeto, pero
		 * ahora con los datos actualizados desde la base de datos Por ejemplo: - Si el
		 * campo id se genera automáticamente, el objeto devuelto ya lo tendrá - Si hay
		 * triggers o campos como fechaCreacion, también vendrán actualizados 
		 * ✅ Entonces
		 * sí, el objeto que regresa es el mismo Usuario, pero ya persistido y completo.
		 */

	}
	
	public List<Usuario> listarTodos() {
	    return usuarioRepository.findAll();
	}
}
