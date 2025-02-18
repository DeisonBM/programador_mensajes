package com.reto.programador_mensajes.controller;

import com.reto.programador_mensajes.model.MensajeEnviado;
import com.reto.programador_mensajes.repository.RepositorioMensajesEnviados;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar los mensajes que ya han sido enviados.
 */
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/sent-messages")
public class ControladorMensajesEnviados {

    private final RepositorioMensajesEnviados repositorio;

    public ControladorMensajesEnviados(RepositorioMensajesEnviados repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public ResponseEntity<List<MensajeEnviado>> obtenerMensajesEnviados() {
        List<MensajeEnviado> mensajes = repositorio.findAll();
        return ResponseEntity.ok(mensajes);
    }
}
