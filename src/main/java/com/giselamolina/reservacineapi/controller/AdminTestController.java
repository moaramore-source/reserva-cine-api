package com.giselamolina.reservacineapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/test")
public class AdminTestController {

    @GetMapping
    public Map<String, String> testAdmin() {
        return Map.of(
                "message", "Acceso autorizado para ROLE_ADMON"
        );
    }
}