package com.giselamolina.reservacineapi.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, unique = true, length = 30)
    private NombrePerfil nombre;

    public Perfil() {
    }

    public Perfil(Long id, NombrePerfil nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public NombrePerfil getNombre() {
        return nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(NombrePerfil nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perfil perfil)) return false;
        return nombre == perfil.nombre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}