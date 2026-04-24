package com.giselamolina.reservacineapi.controller;

import com.giselamolina.reservacineapi.dto.TipoEventoRequest;
import com.giselamolina.reservacineapi.dto.TipoEventoResponse;
import com.giselamolina.reservacineapi.service.TipoEventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tipos")
public class AdminTipoEventoController {

    private final TipoEventoService tipoEventoService;

    public AdminTipoEventoController(TipoEventoService tipoEventoService) {
        this.tipoEventoService = tipoEventoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoEventoResponse>> obtenerTodos() {
        return ResponseEntity.ok(tipoEventoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEventoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoEventoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoEventoResponse> crearTipo(@Valid @RequestBody TipoEventoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoEventoService.crearTipo(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEventoResponse> actualizarTipo(@PathVariable Long id,
                                                             @Valid @RequestBody TipoEventoRequest request) {
        return ResponseEntity.ok(tipoEventoService.actualizarTipo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipo(@PathVariable Long id) {
        tipoEventoService.eliminarTipo(id);
        return ResponseEntity.noContent().build();
    }
}