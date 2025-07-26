package com.Rick.ForoAPI.domain.Curso;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Transactional
    public DetalleCursoDTO nuevoCurso(NuevoCursoDTO nuevoCurso){
        var curso = new Curso(
                null,
                nuevoCurso.nombre(),
                nuevoCurso.categoria(),
                true
        );
        cursoRepository.save(curso);
        return new DetalleCursoDTO(curso);
    }

    public Page<DetalleCursoDTO> listar(Pageable pageable) {
        return cursoRepository.findAll(pageable)
                .map(DetalleCursoDTO::new);
    }

    public DetalleCursoDTO obtenerPorId(Integer id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con id: " + id));
        return new DetalleCursoDTO(curso);
    }

    @Transactional
    public DetalleCursoDTO actualizarCurso(Integer id, NuevoCursoDTO datos) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        curso.setNombre(datos.nombre());
        curso.setCategoria(datos.categoria());

        cursoRepository.save(curso);

        return new DetalleCursoDTO(curso);
    }

    @Transactional
    public void eliminarCurso(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        curso.setActivo(false);
        cursoRepository.save(curso);
    }
}
