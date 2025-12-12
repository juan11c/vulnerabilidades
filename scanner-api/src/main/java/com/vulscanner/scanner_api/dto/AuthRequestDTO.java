package com.vulscanner.scanner_api.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
	private String email;
	private String password;
}
