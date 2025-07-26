package com.Rick.ForoAPI.domain.Respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {
    Page<Respuesta> findByTopicoId(Integer idTopico, Pageable pageable);
}
