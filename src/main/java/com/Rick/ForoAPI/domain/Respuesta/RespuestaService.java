package com.Rick.ForoAPI.domain.Respuesta;

import com.Rick.ForoAPI.domain.Topico.TopicoRepository;
import com.Rick.ForoAPI.domain.Usuario.Usuario;
import com.Rick.ForoAPI.domain.Usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public DatosDetalleRespuestaDTO crearRespuesta(Integer idTopico, NuevaRespuestaDTO dto) {
        var correo = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuario = (Usuario)usuarioRepository.findByCorreo(correo);
        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        Respuesta respuesta = new Respuesta(
                null,
                dto.mensaje(),
                LocalDateTime.now(),
                usuario,
                topico
        );
        respuestaRepository.save(respuesta);
        return new DatosDetalleRespuestaDTO(respuesta);
    }

    public Page<DatosDetalleRespuestaDTO> listarRespuestas(Integer idTopico, Pageable pageable) {
        return respuestaRepository.findByTopicoId(idTopico, pageable)
                .map(DatosDetalleRespuestaDTO::new);
    }

    public DatosDetalleRespuestaDTO obtenerPorId(Integer id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));
        return new DatosDetalleRespuestaDTO(respuesta);
    }

    @Transactional
    public DatosDetalleRespuestaDTO actualizarRespuesta(Integer id, NuevaRespuestaDTO dto) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        String correoUsuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!respuesta.getAutor().getCorreo().equals(correoUsuarioActual)) {
            throw new AccessDeniedException("No tienes permiso para editar esta respuesta");
        }

        if (dto.mensaje() != null && !dto.mensaje().isBlank()) {
            respuesta.setMensaje(dto.mensaje());
        }
        respuestaRepository.save(respuesta);
        return new DatosDetalleRespuestaDTO(respuesta);
    }

    @Transactional
    public void eliminarRespuesta(Integer id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));
        String correoUsuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!respuesta.getAutor().getCorreo().equals(correoUsuarioActual)) {
            throw new AccessDeniedException("No tienes permiso para eliminar esta respuesta");
        }

        respuestaRepository.delete(respuesta);
    }
}
