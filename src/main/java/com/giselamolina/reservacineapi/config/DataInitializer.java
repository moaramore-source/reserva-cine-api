package com.giselamolina.reservacineapi.config;

import com.giselamolina.reservacineapi.entity.NombrePerfil;
import com.giselamolina.reservacineapi.entity.Perfil;
import com.giselamolina.reservacineapi.entity.TipoEvento;
import com.giselamolina.reservacineapi.entity.Usuario;
import com.giselamolina.reservacineapi.repository.PerfilRepository;
import com.giselamolina.reservacineapi.repository.TipoEventoRepository;
import com.giselamolina.reservacineapi.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

//@Component
public class DataInitializer implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final TipoEventoRepository tipoEventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PerfilRepository perfilRepository,
                           TipoEventoRepository tipoEventoRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.perfilRepository = perfilRepository;
        this.tipoEventoRepository = tipoEventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Perfil perfilAdmin = perfilRepository.findByNombre(NombrePerfil.ROLE_ADMON)
                .orElseGet(() -> {
                    Perfil admin = new Perfil();
                    admin.setNombre(NombrePerfil.ROLE_ADMON);
                    return perfilRepository.save(admin);
                });

        perfilRepository.findByNombre(NombrePerfil.ROLE_CLIENTE)
        .orElseGet(() -> {
            Perfil cliente = new Perfil();
            cliente.setNombre(NombrePerfil.ROLE_CLIENTE);
            return perfilRepository.save(cliente);
        });

        if (!usuarioRepository.existsById("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            admin.setEmail("admin@reservacine.com");
            admin.setNombre("Administrador");
            admin.setApellidos("Sistema");
            admin.setDireccion("Oficina central");
            admin.setEnabled(true);
            admin.setFechaRegistro(LocalDate.now());
            admin.setPerfiles(Set.of(perfilAdmin));
            usuarioRepository.save(admin);
        }

        crearTipoSiNoExiste("Estreno", "Películas de estreno");
        crearTipoSiNoExiste("Infantil", "Sesiones de cine infantil");
        crearTipoSiNoExiste("Terror", "Sesiones de cine de terror");
        crearTipoSiNoExiste("Version Original", "Sesiones en versión original");
    }

    private void crearTipoSiNoExiste(String nombre, String descripcion) {
        if (!tipoEventoRepository.existsByNombre(nombre)) {
            TipoEvento tipo = new TipoEvento();
            tipo.setNombre(nombre);
            tipo.setDescripcion(descripcion);
            tipoEventoRepository.save(tipo);
        }
    }
}