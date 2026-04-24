package com.giselamolina.reservacineapi.service.impl;

import com.giselamolina.reservacineapi.dto.CrearAdminRequest;
import com.giselamolina.reservacineapi.dto.PerfilResponse;
import com.giselamolina.reservacineapi.dto.UsuarioResponse;
import com.giselamolina.reservacineapi.entity.NombrePerfil;
import com.giselamolina.reservacineapi.entity.Perfil;
import com.giselamolina.reservacineapi.entity.Usuario;
import com.giselamolina.reservacineapi.exception.ConflictException;
import com.giselamolina.reservacineapi.exception.ResourceNotFoundException;
import com.giselamolina.reservacineapi.repository.PerfilRepository;
import com.giselamolina.reservacineapi.repository.UsuarioRepository;
import com.giselamolina.reservacineapi.service.AdminUsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class AdminUsuarioServiceImpl implements AdminUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUsuarioServiceImpl(UsuarioRepository usuarioRepository,
                                   PerfilRepository perfilRepository,
                                   PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioResponse> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToUsuarioResponse)
                .toList();
    }

    @Override
    public UsuarioResponse obtenerPorUsername(String username) {
        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return mapToUsuarioResponse(usuario);
    }

    @Override
    public UsuarioResponse crearAdmin(CrearAdminRequest request) {
        if (usuarioRepository.existsById(request.getUsername())) {
            throw new ConflictException("El username ya existe");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email ya está registrado");
        }

        Perfil perfilAdmin = perfilRepository.findByNombre(NombrePerfil.ROLE_ADMON)
                .orElseThrow(() -> new ResourceNotFoundException("El perfil ROLE_ADMON no existe"));

        Usuario admin = new Usuario();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setEmail(request.getEmail());
        admin.setNombre(request.getNombre());
        admin.setApellidos(request.getApellidos());
        admin.setDireccion(request.getDireccion());
        admin.setEnabled(true);
        admin.setFechaRegistro(LocalDate.now());
        admin.setPerfiles(Set.of(perfilAdmin));

        usuarioRepository.save(admin);

        return mapToUsuarioResponse(admin);
    }

    @Override
    public UsuarioResponse activarUsuario(String username) {
        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setEnabled(true);
        usuarioRepository.save(usuario);

        return mapToUsuarioResponse(usuario);
    }

    @Override
    public UsuarioResponse desactivarUsuario(String username) {
        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setEnabled(false);
        usuarioRepository.save(usuario);

        return mapToUsuarioResponse(usuario);
    }

    @Override
    public List<PerfilResponse> obtenerPerfiles() {
        return perfilRepository.findAll()
                .stream()
                .map(p -> new PerfilResponse(p.getId(), p.getNombre().name()))
                .toList();
    }

    private UsuarioResponse mapToUsuarioResponse(Usuario usuario) {
        List<String> roles = usuario.getPerfiles()
                .stream()
                .map(p -> p.getNombre().name())
                .toList();

        return new UsuarioResponse(
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getDireccion(),
                usuario.getEnabled(),
                usuario.getFechaRegistro(),
                roles
        );
    }
}