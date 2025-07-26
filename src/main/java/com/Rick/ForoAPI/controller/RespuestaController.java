package com.Rick.ForoAPI.controller;

import com.Rick.ForoAPI.domain.Respuesta.DatosDetalleRespuestaDTO;
import com.Rick.ForoAPI.domain.Respuesta.NuevaRespuestaDTO;
import com.Rick.ForoAPI.domain.Respuesta.RespuestaService;
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
    public class RespuestaController {

        @Autowired
        RespuestaService respuestaService;

        @PostMapping("/topicos/{idTopico}/respuestas")
        public ResponseEntity<DatosDetalleRespuestaDTO> crearRespuesta(
                @PathVariable Integer idTopico,
                @RequestBody @Valid NuevaRespuestaDTO dto
        ) {
            var respuesta = respuestaService.crearRespuesta(idTopico, dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(respuesta.id())
                    .toUri();
            return ResponseEntity.created(location).body(respuesta);
        }

        @GetMapping("/topicos/{idTopico}/respuestas")
        public ResponseEntity<Page<DatosDetalleRespuestaDTO>> listarRespuestas(
                @PathVariable Integer idTopico,
                @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable
        ) {
            return ResponseEntity.ok(respuestaService.listarRespuestas(idTopico, pageable));
        }

        @GetMapping("/respuestas/{id}")
        public ResponseEntity<DatosDetalleRespuestaDTO> obtenerRespuesta(@PathVariable Integer id) {
            return ResponseEntity.ok(respuestaService.obtenerPorId(id));
        }

        @PatchMapping("/respuestas/{id}")
        public ResponseEntity<DatosDetalleRespuestaDTO> actualizarRespuesta(
                @PathVariable Integer id,
                @RequestBody NuevaRespuestaDTO dto
        ) {
            return ResponseEntity.ok(respuestaService.actualizarRespuesta(id, dto));
        }

        @DeleteMapping("/respuestas/{id}")
        public ResponseEntity<Void> borrarRespuesta(@PathVariable Integer id) {
            respuestaService.eliminarRespuesta(id);
            return ResponseEntity.noContent().build();
        }
    }

