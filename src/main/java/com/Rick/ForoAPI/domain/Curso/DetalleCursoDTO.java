package com.Rick.ForoAPI.domain.Curso;

public record DetalleCursoDTO(Integer id, String nombre, Curso.Categoria categoria) {

    public DetalleCursoDTO(Curso curso) {
        this(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
    }
}
