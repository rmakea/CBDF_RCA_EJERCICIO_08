package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    // Aquí puedes agregar consultas personalizadas
    // @Query
}
