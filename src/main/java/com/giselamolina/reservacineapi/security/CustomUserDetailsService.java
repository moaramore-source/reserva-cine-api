package com.giselamolina.reservacineapi.security;

import com.giselamolina.reservacineapi.entity.Usuario;
import com.giselamolina.reservacineapi.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                        usuario.getPerfiles()
                                .stream()
                                .map(perfil -> new SimpleGrantedAuthority(perfil.getNombre().name()))
                                .collect(Collectors.toSet())
                )
                .disabled(!Boolean.TRUE.equals(usuario.getEnabled()))
                .build();
    }
}