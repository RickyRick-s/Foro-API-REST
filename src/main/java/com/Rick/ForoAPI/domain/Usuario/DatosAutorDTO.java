package com.Rick.ForoAPI.domain.Usuario;

import com.Rick.ForoAPI.domain.Rol.Rol;

public record DatosAutorDTO(
                            Integer id,
                            String nombre,
                            String correo,
                            Rol rol
) {}
