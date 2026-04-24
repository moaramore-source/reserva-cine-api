package com.giselamolina.reservacineapi.controller;

import com.giselamolina.reservacineapi.dto.ReservaRequest;
import com.giselamolina.reservacineapi.dto.ReservaResponse;
import com.giselamolina.reservacineapi.service.ReservaService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ReservaResponse crearReserva(@RequestBody ReservaRequest request,
                                        Authentication auth) {
        return reservaService.crearReserva(request, auth.getName());
    }

    @GetMapping
    public List<ReservaResponse> misReservas(Authentication auth) {
        return reservaService.misReservas(auth.getName());
    }

    @PatchMapping("/{id}/cancelar")
    public Map<String, String> cancelar(@PathVariable Long id, Authentication auth) {
        reservaService.cancelarReserva(id, auth.getName());
        return Map.of("message", "Reserva cancelada correctamente");
    }
}