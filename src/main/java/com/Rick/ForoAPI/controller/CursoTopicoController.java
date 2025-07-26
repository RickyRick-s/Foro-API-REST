package com.Rick.ForoAPI.controller;

import com.Rick.ForoAPI.domain.Topico.DatosDetalleTopicoDTO;
import com.Rick.ForoAPI.domain.Topico.NuevoTopicoDTO;
import com.Rick.ForoAPI.domain.Topico.Topico;
import com.Rick.ForoAPI.domain.Topico.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/cursos/{idCurso}/topicos")
public class CursoTopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DatosDetalleTopicoDTO> crearTopico(
            @PathVariable Integer idCurso,
            @RequestBody @Valid NuevoTopicoDTO nuevoTopicoDTO) {
        var dto = topicoService.nuevoTopico(nuevoTopicoDTO, idCurso);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.id())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopicoDTO>> listarTopicosPorCurso(
            @PathVariable Integer idCurso,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {
        Page<Topico> pageTopicos = topicoService.listarTopicosPorCurso(idCurso, pageable);
        Page<DatosDetalleTopicoDTO> pageDTOs = pageTopicos.map(DatosDetalleTopicoDTO::new);
        return ResponseEntity.ok(pageDTOs);
    }
}
