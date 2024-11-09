package com.upiiz.relaciones.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"entrenador", "liga", "competencias", "jugadores"})

public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @OneToMany(mappedBy = "equipo")
    private List<Jugador> jugadores;

    @OneToOne(mappedBy = "equipo")
    private Entrenador entrenador;

    @ManyToMany
    private List<Competencia> competencias;
}
