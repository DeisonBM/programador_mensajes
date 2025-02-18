package com.reto.programador_mensajes.controller;

import com.reto.programador_mensajes.model.MensajeProgramado;
import com.reto.programador_mensajes.service.ServicioMensajesProgramados;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar los mensajes programados.
 */
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api")
public class ControladorMensajesProgramados {

    private final ServicioMensajesProgramados servicio;

    public ControladorMensajesProgramados(ServicioMensajesProgramados servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/message")
    public ResponseEntity<MensajeProgramado> crearMensaje(@RequestBody MensajeProgramado mensaje) {
        if (mensaje.getMensaje() == null || mensaje.getMensaje().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (mensaje.getFechaProgramada() == null) {
            return ResponseEntity.badRequest().build();
        }

        MensajeProgramado mensajeGuardado = servicio.guardarMensaje(mensaje);
        return ResponseEntity.ok(mensajeGuardado);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MensajeProgramado>> buscarMensajes() {
        List<MensajeProgramado> mensajes = servicio.obtenerTodosLosMensajes();
        return ResponseEntity.ok(mensajes);
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        servicio.eliminarMensaje(id);
        return ResponseEntity.noContent().build();
    }
}
