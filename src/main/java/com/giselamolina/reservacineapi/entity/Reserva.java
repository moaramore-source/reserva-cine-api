package com.giselamolina.reservacineapi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private int cantidadEntradas;

    @Column(name = "precio_venta", nullable = false)
    private double precioTotal;

    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Usuario usuario;

    @Column(name = "cancelada")
    private boolean cancelada = false;

    public Reserva() {
    }

    public Long getId() {
        return id;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public Evento getEvento() {
        return evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }
}