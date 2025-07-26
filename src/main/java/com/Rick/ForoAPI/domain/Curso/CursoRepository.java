package com.Rick.ForoAPI.domain.Curso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    Optional<Curso> findById(Integer id);

}
