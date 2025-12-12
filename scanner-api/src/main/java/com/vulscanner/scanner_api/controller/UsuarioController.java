package com.vulscanner.scanner_api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vulscanner.scanner_api.dto.UsuarioDTO;
import com.vulscanner.scanner_api.dto.UsuarioRespuestaDTO;
import com.vulscanner.scanner_api.mapper.UsuarioMapper;
import com.vulscanner.scanner_api.model.Usuario;
import com.vulscanner.scanner_api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/archivos/")
public class UsuarioController {
	private final UsuarioService usuarioService;
	private final UsuarioMapper usuarioMapper;
	
	public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
		this.usuarioService = usuarioService;
		this.usuarioMapper = usuarioMapper;
	}
	
	@PostMapping("crear-usuario")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO request){
		Usuario usuario = usuarioService.registrarUsuario(request);
        UsuarioRespuestaDTO respuesta = usuarioMapper.toRespuestaDTO(usuario);
        return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("listar")
	public ResponseEntity<List<UsuarioRespuestaDTO>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.listarTodos();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.print("Estas autenticado como "+email);
        List<UsuarioRespuestaDTO> respuesta = usuarios.stream()
            .map(usuarioMapper::toRespuestaDTO)
            .toList();
        return ResponseEntity.ok(respuesta);
	}
}
