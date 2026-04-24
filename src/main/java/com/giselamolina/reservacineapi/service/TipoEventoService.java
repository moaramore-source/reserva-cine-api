package com.giselamolina.reservacineapi.service;

import com.giselamolina.reservacineapi.dto.TipoEventoRequest;
import com.giselamolina.reservacineapi.dto.TipoEventoResponse;

import java.util.List;

public interface TipoEventoService {

    TipoEventoResponse crearTipo(TipoEventoRequest request);

    TipoEventoResponse actualizarTipo(Long id, TipoEventoRequest request);

    void eliminarTipo(Long id);

    TipoEventoResponse obtenerPorId(Long id);

    List<TipoEventoResponse> obtenerTodos();
}