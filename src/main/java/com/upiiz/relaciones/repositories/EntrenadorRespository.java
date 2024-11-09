package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenadorRespository extends JpaRepository<Entrenador, Long> {
}
