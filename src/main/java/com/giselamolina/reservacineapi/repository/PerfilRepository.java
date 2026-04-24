package com.giselamolina.reservacineapi.repository;

import com.giselamolina.reservacineapi.entity.NombrePerfil;
import com.giselamolina.reservacineapi.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNombre(NombrePerfil nombre);
}