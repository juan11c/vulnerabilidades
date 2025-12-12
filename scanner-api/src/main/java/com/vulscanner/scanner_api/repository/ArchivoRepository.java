package com.vulscanner.scanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vulscanner.scanner_api.model.Archivo;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
	
}
