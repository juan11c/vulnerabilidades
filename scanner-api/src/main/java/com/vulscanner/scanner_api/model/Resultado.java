package com.vulscanner.scanner_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Resultado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String tipoVulnerabilidad;
    private String severidad;
    private String recomendacion;
    private int lineaDetectada;

    @ManyToOne
    @JoinColumn(name = "archivo_id")
    private Archivo archivo;
    /**
	    * La anotación @ManyToOne indica que muchos objetos de la clase actual (Resultado) están relacionados con uno solo de otra clase (Archivo). Es decir:
		- Un archivo puede tener muchos resultados.
		- Cada resultado pertenece a un solo archivo.
		  Esto se traduce en una relación de base de datos donde la tabla resultado tiene una clave foránea apuntando a la tabla archivo.
     */

}
