
package com.Rick.ForoAPI.domain.Topico;

import com.Rick.ForoAPI.domain.Curso.CursoRepository;
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
public class TopicoService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Transactional
    public DatosDetalleTopicoDTO nuevoTopico(NuevoTopicoDTO nuevoTopico, Integer idCurso){
        var correo = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuario = (Usuario) usuarioRepository.findByCorreo(correo);
        var curso = cursoRepository.findById(idCurso).orElseThrow(() ->
                new EntityNotFoundException("Curso no encontrado"));

        Topico topico = new Topico(
                null,
                nuevoTopico.titulo(),
                nuevoTopico.mensaje(),
                LocalDateTime.now(),
                Topico.Status.ABIERTO,
                usuario,
                curso
        );
        topicoRepository.save(topico);
        return new DatosDetalleTopicoDTO(topico);
    }
    public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    public DatosDetalleTopicoDTO obtenerTopicoPorId(Integer id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con id " + id));
        return new DatosDetalleTopicoDTO(topico);
    }

    @Transactional
    public DatosDetalleTopicoDTO actualizarTopico(Integer idTopico, NuevoTopicoDTO datosActualizar) {
        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        String correoUsuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!topico.getAutor().getCorreo().equals(correoUsuarioActual)) {
            throw new AccessDeniedException("No tienes permiso para editar este tópico");
        }

        if (datosActualizar.titulo() != null && !datosActualizar.titulo().isBlank()) {
            topico.setTitulo(datosActualizar.titulo());
        }
        if (datosActualizar.mensaje() != null && !datosActualizar.mensaje().isBlank()) {
            topico.setMensaje(datosActualizar.mensaje());
        }

        topicoRepository.save(topico);

        return new DatosDetalleTopicoDTO(topico);
    }
    @Transactional
    public void eliminarTopico(Integer id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        String correoUsuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!topico.getAutor().getCorreo().equals(correoUsuarioActual)) {
            throw new AccessDeniedException("No tienes permiso para eliminar este tópico");
        }

        topicoRepository.delete(topico);
    }

    @Transactional
    public DatosDetalleTopicoDTO cerrarTopico(Integer idTopico) {
        Topico topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        topico.setStatus(Topico.Status.CERRADO);
        topicoRepository.save(topico);

        return new DatosDetalleTopicoDTO(topico);
    }

    public Page<Topico> listarTopicosPorCurso(Integer idCurso, Pageable pageable) {
        return topicoRepository.findByCursoId(idCurso, pageable);

    }
}
