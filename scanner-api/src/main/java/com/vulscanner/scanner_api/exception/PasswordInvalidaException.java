package com.vulscanner.scanner_api.exception;

public class PasswordInvalidaException extends RuntimeException {
	public PasswordInvalidaException(String mensaje) {
		super(mensaje);
	}
}
