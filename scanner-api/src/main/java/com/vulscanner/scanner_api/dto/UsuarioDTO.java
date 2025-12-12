package com.vulscanner.scanner_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@Email
	private String email;
	
	private String name;
}
