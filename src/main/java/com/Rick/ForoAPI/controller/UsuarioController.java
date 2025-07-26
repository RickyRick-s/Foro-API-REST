package com.Rick.ForoAPI.controller;

import com.Rick.ForoAPI.domain.Rol.Rol;
import com.Rick.ForoAPI.domain.Rol.RolRepository;
import com.Rick.ForoAPI.domain.Usuario.*;
import com.Rick.ForoAPI.infrastructure.DatosJWTDTO;
import com.Rick.ForoAPI.infrastructure.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity iniciarSesion(@RequestBody @Valid DatosAutenticacionDTO datos ){
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.correo(), datos.contrasena());
        var authenticacion = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generarToken((Usuario) authenticacion.getPrincipal());
        return  ResponseEntity.ok(new DatosJWTDTO(tokenJWT));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/me")
    public ResponseEntity<?> obtenerDatosUsuario(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("No autenticado");
        }
        var datosUsuario = new Object() {
            public String nombre = usuario.getNombre();
            public String correo = usuario.getCorreo();
            public String rol = usuario.getRol().getNombreRol();
        };

        return ResponseEntity.ok(datosUsuario);
    }


    @Transactional
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid DatosRegistroUsuarioDTO datos){
        Rol rol = rolRepository.findByNombreRol("USER")
                .orElseThrow(() -> new RuntimeException("Rol no existe"));

        Usuario nuevoUsuario = new Usuario(
                null,
                datos.nombre(),
                datos.correo(),
                passwordEncoder.encode(datos.contrasena()),
                rol,
                true
        );
        usuarioRepository.save(nuevoUsuario);
        URI location = URI.create("/usuarios/" + nuevoUsuario.getId());
        return ResponseEntity.created(location).body("Usuario registrado correctamente");
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        DatosDetalleUsuarioDTO datos = usuarioService.obtenerDatosUsuarioPorId(id);
        return ResponseEntity.ok(datos);
    }

    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/hacer-admin")
    public ResponseEntity<Void> hacerAdmin(@PathVariable Integer id) {
        usuarioService.asignarRolAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "bearer-key")
    @PatchMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuarioDTO> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody @Valid DatosActualizarUsuarioDTO dto) {
        DatosDetalleUsuarioDTO usuarioActualizado = usuarioService.actualizarUsuarioParcial(id, dto);
        return ResponseEntity.ok(usuarioActualizado);
    }

}
