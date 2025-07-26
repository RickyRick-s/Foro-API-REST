package com.Rick.ForoAPI.controller;

import com.Rick.ForoAPI.domain.Curso.CursoService;
import com.Rick.ForoAPI.domain.Curso.DetalleCursoDTO;
import com.Rick.ForoAPI.domain.Curso.NuevoCursoDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DetalleCursoDTO>nuevoCurso (
            @RequestBody @Valid NuevoCursoDTO nuevoCursoDTO,
            UriComponentsBuilder uriBuilder
            ){
        var dto = cursoService.nuevoCurso(nuevoCursoDTO);
        URI location = uriBuilder.path("/cursos/{id}").buildAndExpand(dto.id()).toUri();
        return  ResponseEntity.created(location).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(@PageableDefault(size = 10, sort = "nombre")Pageable pageable) {
        var pagina = cursoService.listar(pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCursoDTO> obtenerCursoPorId(@PathVariable Integer id) {
        var curso = cursoService.obtenerPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DetalleCursoDTO> actualizarCurso(
            @PathVariable Integer id,
            @RequestBody @Valid NuevoCursoDTO nuevoCursoDTO) {

        var dtoActualizado = cursoService.actualizarCurso(id, nuevoCursoDTO);
        return ResponseEntity.ok(dtoActualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Integer id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }


}
