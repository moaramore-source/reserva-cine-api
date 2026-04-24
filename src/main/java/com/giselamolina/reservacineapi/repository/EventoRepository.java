package com.giselamolina.reservacineapi.repository;

import com.giselamolina.reservacineapi.entity.EstadoEvento;
import com.giselamolina.reservacineapi.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByEstado(EstadoEvento estado);
    List<Evento> findByDestacadoTrue();
    List<Evento> findByEstadoAndDestacadoTrue(EstadoEvento estado);
}