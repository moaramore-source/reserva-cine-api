package com.giselamolina.reservacineapi.controller;

import com.giselamolina.reservacineapi.dto.TipoEventoResponse;
import com.giselamolina.reservacineapi.service.TipoEventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/tipos")
public class PublicTipoEventoController {

    private final TipoEventoService tipoEventoService;

    public PublicTipoEventoController(TipoEventoService tipoEventoService) {
        this.tipoEventoService = tipoEventoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoEventoResponse>> obtenerTodos() {
        return ResponseEntity.ok(tipoEventoService.obtenerTodos());
    }
}