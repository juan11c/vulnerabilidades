package com.vulscanner.scanner_api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;



/**
 *  ¿Por qué en el modelo no se usa @AllArgsConstructor?
	Porque:
	- Las entidades JPA como Usuario necesitan un constructor vacío para que Hibernate pueda instanciarlas al leer desde la base de datos.
	- Si solo tuvieras un constructor lleno, Hibernate fallaría al intentar mapear los datos.

 */
@Data
@Builder
public class UsuarioRespuestaDTO {
	private String message;
	private String name;
	private String email;
	private Long id;
	private LocalDateTime dateCreated;
}
