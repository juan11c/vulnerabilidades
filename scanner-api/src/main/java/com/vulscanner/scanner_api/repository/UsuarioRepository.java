package com.vulscanner.scanner_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vulscanner.scanner_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByUsername(String username);
	Optional<Usuario> findByEmail(String email);
}
