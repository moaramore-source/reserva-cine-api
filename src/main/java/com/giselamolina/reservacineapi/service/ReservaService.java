package com.giselamolina.reservacineapi.service;

import com.giselamolina.reservacineapi.dto.ReservaRequest;
import com.giselamolina.reservacineapi.dto.ReservaResponse;

import java.util.List;

public interface ReservaService {

    ReservaResponse crearReserva(ReservaRequest request, String username);

    List<ReservaResponse> misReservas(String username);

    void cancelarReserva(Long id, String username);
}