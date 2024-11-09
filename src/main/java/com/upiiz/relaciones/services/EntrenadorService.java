package com.upiiz.relaciones.services;


import com.upiiz.relaciones.entities.Entrenador;
import com.upiiz.relaciones.repositories.EntrenadorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRespository entrenadorRepository;

    public List<Entrenador> getEntrenadores() {
        return entrenadorRepository.findAll();
    }

    public Optional<Entrenador> getEntrenadorById(Long id) {
        return entrenadorRepository.findById(id);
    }

    public Entrenador createEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador updateEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public void deleteEntrenador(Long id) {
        entrenadorRepository.deleteById(id);
    }
}