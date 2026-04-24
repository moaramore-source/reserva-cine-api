package com.giselamolina.reservacineapi.service.impl;

import com.giselamolina.reservacineapi.dto.AuthResponse;
import com.giselamolina.reservacineapi.dto.LoginRequest;
import com.giselamolina.reservacineapi.dto.RegisterRequest;
import com.giselamolina.reservacineapi.entity.NombrePerfil;
import com.giselamolina.reservacineapi.entity.Perfil;
import com.giselamolina.reservacineapi.entity.Usuario;
import com.giselamolina.reservacineapi.exception.ConflictException;
import com.giselamolina.reservacineapi.exception.ResourceNotFoundException;
import com.giselamolina.reservacineapi.repository.PerfilRepository;
import com.giselamolina.reservacineapi.repository.UsuarioRepository;
import com.giselamolina.reservacineapi.security.CustomUserDetailsService;
import com.giselamolina.reservacineapi.security.JwtService;
import com.giselamolina.reservacineapi.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           PerfilRepository perfilRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           CustomUserDetailsService customUserDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsById(request.getUsername())) {
            throw new ConflictException("El username ya existe");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email ya está registrado");
        }

        Perfil perfilCliente = perfilRepository.findByNombre(NombrePerfil.ROLE_CLIENTE)
                .orElseThrow(() -> new ResourceNotFoundException("El perfil ROLE_CLIENTE no existe"));

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEmail(request.getEmail());
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setDireccion(request.getDireccion());
        usuario.setEnabled(true);
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setPerfiles(Set.of(perfilCliente));

        usuarioRepository.save(usuario);

        return new AuthResponse(
                "Usuario registrado correctamente",
                usuario.getUsername(),
                "ROLE_CLIENTE"
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        String role = usuario.getPerfiles()
                .stream()
                .findFirst()
                .map(perfil -> perfil.getNombre().name())
                .orElse("SIN_ROL");

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(usuario.getUsername());
        String jwtToken = jwtService.generateToken(userDetails, role);

        return new AuthResponse(
                "Login correcto",
                usuario.getUsername(),
                role,
                jwtToken
        );
    }
}