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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;


    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopicoDTO>> listarTopicos(
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {
        Page<Topico> pageTopicos = topicoService.listarTopicos(pageable);
        Page<DatosDetalleTopicoDTO> pageDTOs = pageTopicos.map(DatosDetalleTopicoDTO::new);
        return ResponseEntity.ok(pageDTOs);
        }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopicoDTO> obtenerTopico(@PathVariable Integer id) {
        DatosDetalleTopicoDTO dto = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DatosDetalleTopicoDTO> actualizarTopico(
            @PathVariable Integer id,
            @RequestBody NuevoTopicoDTO datosActualizar) {
        var dtoActualizado = topicoService.actualizarTopico(id, datosActualizar);
        return ResponseEntity.ok(dtoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Integer id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<DatosDetalleTopicoDTO> cerrarTopico(@PathVariable Integer id) {
        var dto = topicoService.cerrarTopico(id);
        return ResponseEntity.ok(dto);
    }
}


