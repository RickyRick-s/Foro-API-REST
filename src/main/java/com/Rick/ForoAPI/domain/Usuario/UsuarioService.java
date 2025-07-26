package com.Rick.ForoAPI.domain.Usuario;

import com.Rick.ForoAPI.domain.Rol.Rol;
import com.Rick.ForoAPI.domain.Rol.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public DatosDetalleUsuarioDTO obtenerDatosUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id " + id));
        return new DatosDetalleUsuarioDTO(usuario);
    }

    @Transactional
    public void asignarRolAdmin(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id " + idUsuario));

        Rol rolAdmin = rolRepository.findByNombreRol("ADMIN")
                .orElseThrow(() -> new EntityNotFoundException("Rol ADMIN no encontrado"));

        usuario.setRol(rolAdmin);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public DatosDetalleUsuarioDTO actualizarUsuarioParcial(Integer id, DatosActualizarUsuarioDTO datosActualizar) {
        String correoAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!usuario.getCorreo().equals(correoAutenticado)) {
            throw new AccessDeniedException("No tienes permiso para modificar este usuario");
        }

        if (datosActualizar.nombre() != null && !datosActualizar.nombre().isBlank()) {
            usuario.setNombre(datosActualizar.nombre());
        }
        if (datosActualizar.contrasena() != null && !datosActualizar.contrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(datosActualizar.contrasena()));
        }

        usuarioRepository.save(usuario);

        return new DatosDetalleUsuarioDTO(usuario);
    }

}
