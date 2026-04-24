package com.giselamolina.reservacineapi.controller;

import com.giselamolina.reservacineapi.dto.CrearAdminRequest;
import com.giselamolina.reservacineapi.dto.PerfilResponse;
import com.giselamolina.reservacineapi.dto.UsuarioResponse;
import com.giselamolina.reservacineapi.service.AdminUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminUsuarioController {

    private final AdminUsuarioService adminUsuarioService;

    public AdminUsuarioController(AdminUsuarioService adminUsuarioService) {
        this.adminUsuarioService = adminUsuarioService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponse>> obtenerTodos() {
        return ResponseEntity.ok(adminUsuarioService.obtenerTodos());
    }

    @GetMapping("/usuarios/{username}")
    public ResponseEntity<UsuarioResponse> obtenerPorUsername(@PathVariable String username) {
        return ResponseEntity.ok(adminUsuarioService.obtenerPorUsername(username));
    }

    @PostMapping("/usuarios/admin")
    public ResponseEntity<UsuarioResponse> crearAdmin(@Valid @RequestBody CrearAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminUsuarioService.crearAdmin(request));
    }

    @PatchMapping("/usuarios/{username}/activar")
    public ResponseEntity<UsuarioResponse> activar(@PathVariable String username) {
        return ResponseEntity.ok(adminUsuarioService.activarUsuario(username));
    }

    @PatchMapping("/usuarios/{username}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivar(@PathVariable String username) {
        return ResponseEntity.ok(adminUsuarioService.desactivarUsuario(username));
    }

    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilResponse>> obtenerPerfiles() {
        return ResponseEntity.ok(adminUsuarioService.obtenerPerfiles());
    }
}