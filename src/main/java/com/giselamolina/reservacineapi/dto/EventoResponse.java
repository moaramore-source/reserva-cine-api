package com.giselamolina.reservacineapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private Integer duracion;
    private String direccion;
    private String estado;
    private Boolean destacado;
    private Integer aforoMaximo;
    private Integer minimoAsistencia;
    private BigDecimal precio;
    private Long tipoEventoId;
    private String tipoEventoNombre;
    private Integer plazasDisponibles;

    public EventoResponse() {
    }

    public EventoResponse(Long id, String nombre, String descripcion, LocalDate fechaInicio,
                          Integer duracion, String direccion, String estado, Boolean destacado,
                          Integer aforoMaximo, Integer minimoAsistencia, BigDecimal precio,
                          Long tipoEventoId, String tipoEventoNombre, Integer plazasDisponibles) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.direccion = direccion;
        this.estado = estado;
        this.destacado = destacado;
        this.aforoMaximo = aforoMaximo;
        this.minimoAsistencia = minimoAsistencia;
        this.precio = precio;
        this.tipoEventoId = tipoEventoId;
        this.tipoEventoNombre = tipoEventoNombre;
        this.plazasDisponibles = plazasDisponibles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public Integer getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(Integer aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public Integer getMinimoAsistencia() {
        return minimoAsistencia;
    }

    public void setMinimoAsistencia(Integer minimoAsistencia) {
        this.minimoAsistencia = minimoAsistencia;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getTipoEventoId() {
        return tipoEventoId;
    }

    public void setTipoEventoId(Long tipoEventoId) {
        this.tipoEventoId = tipoEventoId;
    }

    public String getTipoEventoNombre() {
        return tipoEventoNombre;
    }

    public void setTipoEventoNombre(String tipoEventoNombre) {
        this.tipoEventoNombre = tipoEventoNombre;
    }

    public Integer getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public void setPlazasDisponibles(Integer plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }
}