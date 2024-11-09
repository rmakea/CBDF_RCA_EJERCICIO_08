package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.CustomResponse;
import com.upiiz.relaciones.entities.Jugador;
import com.upiiz.relaciones.services.JugadorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/jugadores")
@Tag(
        name = "Jugador"
)
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Jugador>>> getJugadores() {
        Link selfLink = linkTo(methodOn(JugadorController.class).getJugadores()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            List<Jugador> jugadores = jugadorService.getJugadores();
            if (!jugadores.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Jugadores encontrados", jugadores, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron jugadores", jugadores, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Jugador>> getJugadorById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(JugadorController.class).getJugadorById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<Jugador> jugador = jugadorService.getJugadorById(id);
            if (jugador.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Jugador encontrado", jugador.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Jugador no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Jugador>> createJugador(@RequestBody Jugador jugador) {
        Link selfLink = linkTo(methodOn(JugadorController.class).createJugador(jugador)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Jugador nuevoJugador = jugadorService.createJugador(jugador);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Jugador creado exitosamente", nuevoJugador, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear el jugador", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Jugador>> updateJugador(
            @PathVariable Long id, @RequestBody Jugador jugador) {
        Link selfLink = linkTo(methodOn(JugadorController.class).updateJugador(id, jugador)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            jugador.setId(id);
            if (jugadorService.getJugadorById(id).isPresent()) {
                Jugador jugadorActualizado = jugadorService.updateJugador(jugador);
                return ResponseEntity.ok(new CustomResponse<>(1, "Jugador actualizado exitosamente", jugadorActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Jugador no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar el jugador", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteJugador(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(JugadorController.class).deleteJugador(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (jugadorService.getJugadorById(id).isPresent()) {
                jugadorService.deleteJugador(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "Jugador eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Jugador no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar el jugador", null, links));
        }
    }
}
