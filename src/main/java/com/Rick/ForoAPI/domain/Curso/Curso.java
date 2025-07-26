package com.Rick.ForoAPI.domain.Curso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public enum Categoria{
        BACKEND,
        FRONTEND,
        UI_UX_DESIGN,
        BASE_DE_DATOS,
        SOFT_SKILLS,
        OTRO
    }
    private Boolean activo = true;
}
