package com.Rick.ForoAPI.domain.Topico;

import com.Rick.ForoAPI.domain.Curso.Curso;
import com.Rick.ForoAPI.domain.Curso.DetalleCursoDTO;
import com.Rick.ForoAPI.domain.Usuario.DatosAutorDTO;
import com.Rick.ForoAPI.domain.Usuario.Usuario;

import java.time.LocalDateTime;

public record DatosDetalleTopicoDTO(Integer id, String titulo, String mensaje, LocalDateTime fechaCreacion, Topico.Status status,
                                    DatosAutorDTO autor, DetalleCursoDTO curso) {

    public DatosDetalleTopicoDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new DatosAutorDTO(
                        topico.getAutor().getId(),
                        topico.getAutor().getNombre(),
                        topico.getAutor().getCorreo(),
                        topico.getAutor().getRol()
                ),
                new DetalleCursoDTO(topico.getCurso())
        );
    }
}
