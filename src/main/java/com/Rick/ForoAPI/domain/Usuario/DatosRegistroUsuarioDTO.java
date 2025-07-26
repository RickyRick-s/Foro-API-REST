package com.Rick.ForoAPI.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuarioDTO(
        @NotBlank String nombre,
        @NotBlank @Email String correo,
        @NotBlank String contrasena
) {
}