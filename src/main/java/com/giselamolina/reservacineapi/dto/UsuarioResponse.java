package com.giselamolina.reservacineapi.dto;

import java.time.LocalDate;
import java.util.List;

public class UsuarioResponse {

    private String username;
    private String email;
    private String nombre;
    private String apellidos;
    private String direccion;
    private Boolean enabled;
    private LocalDate fechaRegistro;
    private List<String> roles;

    public UsuarioResponse() {
    }

    public UsuarioResponse(String username, String email, String nombre, String apellidos,
                           String direccion, Boolean enabled, LocalDate fechaRegistro,
                           List<String> roles) {
        this.username = username;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.enabled = enabled;
        this.fechaRegistro = fechaRegistro;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}