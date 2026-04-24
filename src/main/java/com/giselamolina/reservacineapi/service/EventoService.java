package com.giselamolina.reservacineapi.service;

import com.giselamolina.reservacineapi.dto.EventoRequest;
import com.giselamolina.reservacineapi.dto.EventoResponse;

import java.util.List;

public interface EventoService {

    EventoResponse crearEvento(EventoRequest request);

    EventoResponse actualizarEvento(Long id, EventoRequest request);

    EventoResponse cancelarEvento(Long id);

    EventoResponse obtenerEventoPorId(Long id);

    List<EventoResponse> obtenerTodos();

    List<EventoResponse> obtenerActivos();

    List<EventoResponse> obtenerDestacados();

    List<EventoResponse> obtenerCancelados();

    List<EventoResponse> obtenerTerminados();
}