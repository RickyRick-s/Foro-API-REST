package com.Rick.ForoAPI.domain.Usuario;

public record DatosDetalleUsuarioDTO(
        String nombre,
        String correo,
        String rol
) {
    public DatosDetalleUsuarioDTO(Usuario usuario) {
        this(
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol().getNombreRol()
        );
    }
}
