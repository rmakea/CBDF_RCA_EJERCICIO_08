package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.CustomResponse;
import com.upiiz.relaciones.entities.Liga;
import com.upiiz.relaciones.services.LigaService;
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
@RequestMapping("/api/ligas")
@Tag(
        name = "Liga"
)
public class LigaController {

    @Autowired
    private LigaService ligaService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Liga>>> getLigas() {
        Link selfLink = linkTo(methodOn(LigaController.class).getLigas()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            List<Liga> ligas = ligaService.getLigas();
            if (!ligas.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Ligas encontradas", ligas, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron ligas", ligas, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> getLigaById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(LigaController.class).getLigaById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<Liga> liga = ligaService.getLigaById(id);
            if (liga.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Liga encontrada", liga.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Liga no encontrada", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Liga>> createLiga(@RequestBody Liga liga) {
        Link selfLink = linkTo(methodOn(LigaController.class).createLiga(liga)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Liga nuevaLiga = ligaService.createLiga(liga);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Liga creada exitosamente", nuevaLiga, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear la liga", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> updateLiga(@PathVariable Long id, @RequestBody Liga liga) {
        Link selfLink = linkTo(methodOn(LigaController.class).updateLiga(id, liga)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            liga.setId(id);
            if (ligaService.getLigaById(id).isPresent()) {
                Liga ligaActualizada = ligaService.updateLiga(liga);
                return ResponseEntity.ok(new CustomResponse<>(1, "Liga actualizada exitosamente", ligaActualizada, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Liga no encontrada", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar la liga", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteLiga(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(LigaController.class).deleteLiga(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (ligaService.getLigaById(id).isPresent()) {
                ligaService.deleteLiga(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "Liga eliminada exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Liga no encontrada", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar la liga", null, links));
        }
    }
}
