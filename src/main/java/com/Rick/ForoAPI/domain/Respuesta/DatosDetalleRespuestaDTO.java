package com.Rick.ForoAPI.domain.Respuesta;

import com.Rick.ForoAPI.domain.Usuario.DatosAutorDTO;
import com.Rick.ForoAPI.domain.Usuario.Usuario;

import java.time.LocalDateTime;

public record DatosDetalleRespuestaDTO(
        Integer id,
        String mensaje,
        LocalDateTime fechaCreacion,
        DatosAutorDTO autor
) {
    public DatosDetalleRespuestaDTO(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                new DatosAutorDTO(
                        respuesta.getAutor().getId(),
                        respuesta.getAutor().getNombre(),
                        respuesta.getAutor().getCorreo(),
                        respuesta.getAutor().getRol()
                )
        );
    }
}
