package com.vulscanner.scanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vulscanner.scanner_api.model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

}
