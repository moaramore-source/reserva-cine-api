package com.giselamolina.reservacineapi.repository;

import com.giselamolina.reservacineapi.entity.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long> {
    Optional<TipoEvento> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}