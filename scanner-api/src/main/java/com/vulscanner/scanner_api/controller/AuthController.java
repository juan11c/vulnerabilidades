package com.vulscanner.scanner_api.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vulscanner.scanner_api.dto.AuthRequestDTO;
import com.vulscanner.scanner_api.dto.AuthResponseDTO;
import com.vulscanner.scanner_api.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;





@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@Operation(summary = "Autenticar usuario y generar token JWT")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
	        content = @Content(mediaType = "application/json",
	            examples = @ExampleObject(
	                name = "Token generado",
	                value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }"
	            )
	        )
	    ),
	    @ApiResponse(responseCode = "401", description = "Contraseña inválida",
	        content = @Content(mediaType = "application/json",
	            examples = @ExampleObject(
	                name = "Error de autenticación",
	                value = "{ \"message\": \"Credenciales inválidas\", \"status\": 401, \"timestamp\": \"2025-09-25T20:00:00\" }"
	            )
	        )
	    ),
	    @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
	        content = @Content(mediaType = "application/json",
	            examples = @ExampleObject(
	                name = "Usuario no existe",
	                value = "{ \"message\": \"Usuario con email juan@example.com no encontrado\", \"status\": 404, \"timestamp\": \"2025-09-25T20:00:00\" }"
	            )
	        )
	    )
	})

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
		String token = authService.authenticate(request.getEmail(), request.getPassword());
		return ResponseEntity.ok(new AuthResponseDTO(token));
	}
}