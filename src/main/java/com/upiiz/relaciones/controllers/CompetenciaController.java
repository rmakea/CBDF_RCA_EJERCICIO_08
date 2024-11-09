package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.CustomResponse;
import com.upiiz.relaciones.entities.Competencia;
import com.upiiz.relaciones.services.CompetenciaService;
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
@RequestMapping("/api/competencias")
@Tag(
        name = "Competencia"
)
public class CompetenciaController {

    @Autowired
    private CompetenciaService competenciaService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Competencia>>> getCompetencias() {
        List<Competencia> competencias;
        Link selfLink = linkTo(methodOn(CompetenciaController.class).getCompetencias()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            competencias = competenciaService.getCompetencias();
            if (!competencias.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Competencias encontradas", competencias, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron competencias", competencias, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Competencia>> getCompetenciaById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(CompetenciaController.class).getCompetenciaById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<Competencia> competencia = competenciaService.getCompetenciaById(id);
            if (competencia.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Competencia encontrada", competencia.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Competencia no encontrada", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Competencia>> createCompetencia(@RequestBody Competencia competencia) {
        Link selfLink = linkTo(methodOn(CompetenciaController.class).createCompetencia(competencia)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Competencia nuevaCompetencia = competenciaService.createCompetencia(competencia);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Competencia creada exitosamente", nuevaCompetencia, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear la competencia", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Competencia>> updateCompetencia(
            @PathVariable Long id, @RequestBody Competencia competencia) {
        Link selfLink = linkTo(methodOn(CompetenciaController.class).updateCompetencia(id, competencia)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            competencia.setId(id);
            if (competenciaService.getCompetenciaById(id).isPresent()) {
                Competencia competenciaActualizada = competenciaService.updateCompetencia(competencia);
                return ResponseEntity.ok(new CustomResponse<>(1, "Competencia actualizada exitosamente", competenciaActualizada, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Competencia no encontrada", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar la competencia", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteCompetencia(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(CompetenciaController.class).deleteCompetencia(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (competenciaService.getCompetenciaById(id).isPresent()) {
                competenciaService.deleteCompetencia(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "Competencia eliminada exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Competencia no encontrada", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar la competencia", null, links));
        }
    }
}
