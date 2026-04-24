package com.giselamolina.reservacineapi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoEvento estado;

    @Column(name = "destacado", nullable = false)
    private Boolean destacado;

    @Column(name = "aforo_maximo", nullable = false)
    private Integer aforoMaximo;

    @Column(name = "minimo_asistencia")
    private Integer minimoAsistencia;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoEvento tipoEvento;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Evento() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public String getDireccion() {
        return direccion;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public Integer getAforoMaximo() {
        return aforoMaximo;
    }

    public Integer getMinimoAsistencia() {
        return minimoAsistencia;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public void setAforoMaximo(Integer aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public void setMinimoAsistencia(Integer minimoAsistencia) {
        this.minimoAsistencia = minimoAsistencia;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}