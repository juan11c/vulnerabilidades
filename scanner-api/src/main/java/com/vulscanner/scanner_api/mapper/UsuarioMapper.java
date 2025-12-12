package com.vulscanner.scanner_api.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.vulscanner.scanner_api.dto.UsuarioDTO;
import com.vulscanner.scanner_api.dto.UsuarioRespuestaDTO;
import com.vulscanner.scanner_api.model.Usuario;

@Component
public class UsuarioMapper {
	
	public UsuarioRespuestaDTO toRespuestaDTO(Usuario usuario) {
        return UsuarioRespuestaDTO.builder()
            .id(usuario.getId())
            .name(usuario.getName())
            .email(usuario.getEmail())
            .message("Usuario cargado correctamente")
            .dateCreated(usuario.getFechaCreacion())
            .build();
    }

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword()); // ⚠️ Se encripta en el service
        usuario.setEmail(dto.getEmail());
        usuario.setName(dto.getName());
        usuario.setFechaCreacion(LocalDateTime.now());
        return usuario;
    }

}
