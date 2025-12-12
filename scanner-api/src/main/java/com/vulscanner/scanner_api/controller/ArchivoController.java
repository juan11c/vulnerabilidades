package com.vulscanner.scanner_api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vulscanner.scanner_api.dto.ArchivoDTO;
import com.vulscanner.scanner_api.model.Archivo;
import com.vulscanner.scanner_api.model.Resultado;
import com.vulscanner.scanner_api.service.ArchivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {
    private final ArchivoService archivoService;

    public ArchivoController(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    @Operation(summary = "Subir un archivo a la base de datos")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Archivo subido con éxito",
	        content = @Content(mediaType = "application/json",
	            examples = @ExampleObject(
	                name = "Archivo subido",
	                value = "{ \"nombre\": \"main.java\", \"contenido\": \"public void main\" }"
	            )
	        )
	    )})
    @PostMapping
    public ResponseEntity<Archivo> subirArchivo(@RequestBody ArchivoDTO request) {
        Archivo archivo = archivoService.guardarArchivo(request.getNombre(), request.getContenido());
        return ResponseEntity.ok(archivo);
    }

    @GetMapping("/{id}/resultados")
    public ResponseEntity<List<Resultado>> obtenerResultados(@PathVariable Long id) {
        List<Resultado> resultados = archivoService.obtenerResultados(id);
        return ResponseEntity.ok(resultados);
    }
    
    @GetMapping
    public ResponseEntity<List<Resultado>> obtenerTodosResultados(){
    	List<Resultado> resultados = archivoService.obtenerTodosResultados();
    	return ResponseEntity.ok(resultados);
    }

}
