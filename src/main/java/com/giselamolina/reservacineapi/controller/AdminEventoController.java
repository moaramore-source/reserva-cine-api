package com.giselamolina.reservacineapi.controller;

import com.giselamolina.reservacineapi.dto.EventoRequest;
import com.giselamolina.reservacineapi.dto.EventoResponse;
import com.giselamolina.reservacineapi.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/eventos")
public class AdminEventoController {

    private final EventoService eventoService;

    public AdminEventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponse> crearEvento(@Valid @RequestBody EventoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crearEvento(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> actualizarEvento(@PathVariable Long id,
                                                           @Valid @RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.actualizarEvento(id, request));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EventoResponse> cancelarEvento(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.cancelarEvento(id));
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> obtenerTodos() {
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerEventoPorId(id));
    }
}