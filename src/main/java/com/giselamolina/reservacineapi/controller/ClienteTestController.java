package com.giselamolina.reservacineapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cliente/test")
public class ClienteTestController {

    @GetMapping
    public Map<String, String> testCliente() {
        return Map.of(
                "message", "Acceso autorizado para ROLE_CLIENTE"
        );
    }
}