package com.vulscanner.scanner_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity //le dice que esta clase es una entidad JPA que se mapera a la base de datos
@Data //ahorra mucho codigo viene de lombok es para getters, setters, to string sin escribirlo
public class Archivo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Se genera el valor auto incremental
	private Long id;
	
	private String nombre;
	private String contenido;
	private LocalDateTime fechaSubida;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

}
