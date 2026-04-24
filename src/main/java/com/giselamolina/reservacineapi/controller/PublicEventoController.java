package com.giselamolina.reservacineapi.controller;

import com.giselamolina.reservacineapi.dto.EventoResponse;
import com.giselamolina.reservacineapi.service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/eventos")
public class PublicEventoController {

    private final EventoService eventoService;

    public PublicEventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> obtenerTodos() {
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EventoResponse>> obtenerActivos() {
        return ResponseEntity.ok(eventoService.obtenerActivos());
    }

    @GetMapping("/destacados")
    public ResponseEntity<List<EventoResponse>> obtenerDestacados() {
        return ResponseEntity.ok(eventoService.obtenerDestacados());
    }

    @GetMapping("/cancelados")
    public ResponseEntity<List<EventoResponse>> obtenerCancelados() {
        return ResponseEntity.ok(eventoService.obtenerCancelados());
    }

    @GetMapping("/terminados")
    public ResponseEntity<List<EventoResponse>> obtenerTerminados() {
        return ResponseEntity.ok(eventoService.obtenerTerminados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerEventoPorId(id));
    }
}