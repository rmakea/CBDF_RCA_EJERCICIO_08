package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.CustomResponse;
import com.upiiz.relaciones.entities.Equipo;
import com.upiiz.relaciones.services.EquipoService;
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
@RequestMapping("/api/equipos")
@Tag(
        name = "Equipo"
)
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Equipo>>> getEquipos() {
        Link selfLink = linkTo(methodOn(EquipoController.class).getEquipos()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            List<Equipo> equipos = equipoService.getEquipos();
            if (!equipos.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Equipos encontrados", equipos, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron equipos", equipos, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipo>> getEquipoById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(EquipoController.class).getEquipoById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<Equipo> equipo = equipoService.getEquipoById(id);
            if (equipo.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Equipo encontrado", equipo.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Equipo>> createEquipo(@RequestBody Equipo equipo) {
        Link selfLink = linkTo(methodOn(EquipoController.class).createEquipo(equipo)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Equipo nuevoEquipo = equipoService.createEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Equipo creado exitosamente", nuevoEquipo, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear el equipo", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipo>> updateEquipo(
            @PathVariable Long id, @RequestBody Equipo equipo) {
        Link selfLink = linkTo(methodOn(EquipoController.class).updateEquipo(id, equipo)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            equipo.setId(id);
            if (equipoService.getEquipoById(id).isPresent()) {
                Equipo equipoActualizado = equipoService.updateEquipo(equipo);
                return ResponseEntity.ok(new CustomResponse<>(1, "Equipo actualizado exitosamente", equipoActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar el equipo", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteEquipo(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(EquipoController.class).deleteEquipo(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (equipoService.getEquipoById(id).isPresent()) {
                equipoService.deleteEquipo(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "Equipo eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar el equipo", null, links));
        }
    }
}
