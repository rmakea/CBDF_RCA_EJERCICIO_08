package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}
