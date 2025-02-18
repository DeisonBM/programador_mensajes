package com.reto.programador_mensajes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "mensajes_enviados"
)
public class MensajeEnviado {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String mensaje;
    @Column(
            nullable = false
    )
    private LocalDateTime fechaProgramada;
    @Column(
            nullable = false
    )
    private LocalDateTime fechaEnviada;

    public MensajeEnviado() {
    }

    public MensajeEnviado(String mensaje, LocalDateTime fechaProgramada, LocalDateTime fechaEnviada) {
        this.mensaje = mensaje;
        this.fechaProgramada = fechaProgramada;
        this.fechaEnviada = fechaEnviada;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaProgramada() {
        return this.fechaProgramada;
    }

    public void setFechaProgramada(LocalDateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public LocalDateTime getFechaEnviada() {
        return this.fechaEnviada;
    }

    public void setFechaEnviada(LocalDateTime fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }
}