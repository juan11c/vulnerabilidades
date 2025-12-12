package com.vulscanner.scanner_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vulscanner.scanner_api.model.Archivo;
import com.vulscanner.scanner_api.model.Resultado;
import com.vulscanner.scanner_api.repository.ArchivoRepository;
import com.vulscanner.scanner_api.repository.ResultadoRepository;

@Service
public class ArchivoService {
	/**
	* 🧱 final
	- Indica que una vez que se asigna el valor, no puede cambiar.
	- Esto garantiza que el repositorio siempre será el mismo durante la vida del objeto ArchivoService.
	  En otras palabras: estás diciendo “esta dependencia es obligatoria y no debe cambiar nunca”. Eso hace que tu clase sea inmutable y más segura.
    */
	
	private final ArchivoRepository archivoRepository;
    private final ResultadoRepository resultadoRepository;
    
    /**
     * 
     * Esto se llama inyección de dependencias por constructor, 
     * y es la forma más recomendada en Spring Boot
     * 
     * ✅ Ventajas:
		- Obligatoriedad: si no se pasan las dependencias, el objeto no se puede crear.
		- Testabilidad: puedes crear instancias manuales en pruebas unitarias.
		- Inmutabilidad: junto con final, asegura que las dependencias no cambien.
		- Claridad: ves claramente qué necesita tu clase para funcionar.
		Spring detecta este constructor y automáticamente inyecta las instancias necesarias (gracias a su contenedor IoC).
		
		¿Y por qué no usar @Autowired directamente?
			Podrías hacer esto:
			@Autowired
			private ArchivoRepository archivoRepository;
			
			
			Pero eso se llama inyección por campo, y aunque funciona, no es la más profesional porque:
			- No puedes ver fácilmente qué necesita la clase.
			- Es más difícil de testear.
			- Rompe el principio de inversión de dependencias.
     * 
     */

    public ArchivoService(ArchivoRepository archivoRepository, ResultadoRepository resultadoRepository) {
        this.archivoRepository = archivoRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public Archivo guardarArchivo(String nombre, String contenido) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombre);
        archivo.setContenido(contenido);
        archivo.setFechaSubida(LocalDateTime.now());
        Archivo guardado = archivoRepository.save(archivo);

        // Simulación de análisis
        Resultado resultado = new Resultado();
        resultado.setArchivo(guardado);
        resultado.setTipoVulnerabilidad("Uso de eval()");
        resultado.setSeveridad("Alta");
        resultado.setRecomendacion("Evita usar eval, usa funciones seguras.");
        resultado.setLineaDetectada(3);

        resultadoRepository.save(resultado);

        return guardado;
    }

    public List<Resultado> obtenerResultados(Long archivoId) {
        return resultadoRepository.findAll()
                .stream()
                .filter(r -> r.getArchivo().getId().equals(archivoId))
                .toList();
    }
    
    public List<Resultado> obtenerTodosResultados(){
    	return resultadoRepository.findAll();
    }

}
