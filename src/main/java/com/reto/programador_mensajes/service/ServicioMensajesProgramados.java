package com.reto.programador_mensajes.service;

import com.reto.programador_mensajes.model.MensajeEnviado;
import com.reto.programador_mensajes.model.MensajeProgramado;
import com.reto.programador_mensajes.repository.RepositorioMensajesEnviados;
import com.reto.programador_mensajes.repository.RepositorioMensajesProgramados;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de mensajes programados y su envío automático.
 */
@Service
public class ServicioMensajesProgramados {

    private static final Logger logger = LoggerFactory.getLogger(ServicioMensajesProgramados.class);
    private final RepositorioMensajesProgramados repositorioMensajesProgramados;
    private final RepositorioMensajesEnviados repositorioMensajesEnviados;
    private final ServicioDiscord servicioDiscord;

    public ServicioMensajesProgramados(RepositorioMensajesProgramados repositorioMensajesProgramados,
                                       RepositorioMensajesEnviados repositorioMensajesEnviados,
                                       ServicioDiscord servicioDiscord) {
        this.repositorioMensajesProgramados = repositorioMensajesProgramados;
        this.repositorioMensajesEnviados = repositorioMensajesEnviados;
        this.servicioDiscord = servicioDiscord;
    }

    public MensajeProgramado guardarMensaje(MensajeProgramado mensaje) {
        logger.info("✅ Guardando mensaje programado: {}", mensaje.getMensaje());
        return repositorioMensajesProgramados.save(mensaje);
    }

    //Obtenemos lista de los mensajes programados.
    public List<MensajeProgramado> obtenerTodosLosMensajes() {
        return repositorioMensajesProgramados.findAll();
    }

    //Desde aqui eliminamos un mensaje programado.
    public void eliminarMensaje(Long id) {
        logger.info("🗑️ Eliminando mensaje con ID: {}", id);
        repositorioMensajesProgramados.deleteById(id);
    }

    // Obtenemos todos los mensajes enviados.
    public List<MensajeEnviado> obtenerMensajesEnviados() {
        return repositorioMensajesEnviados.findAll();
    }

    // Verifica y envía automáticamente los mensajes programados.

    @Scheduled(fixedRate = 3000) // Se ejecuta cada 30 segundos
    @Transactional
    public void enviarMensajesProgramados() {
        logger.info("⏳ Ejecutando tarea programada para enviar mensajes...");


        List<MensajeProgramado> mensajes = repositorioMensajesProgramados.findByFechaProgramadaBefore(LocalDateTime.now());

        if (mensajes.isEmpty()) {
            logger.info("📭 No hay mensajes programados para enviar.");
            return;
        }

        for (MensajeProgramado msg : mensajes) {
            logger.info("📤 Enviando mensaje programado a Discord: {}", msg.getMensaje());

            try {
                servicioDiscord.enviarMensajeAlCanal(msg.getMensaje());

                MensajeEnviado mensajeEnviado = new MensajeEnviado(msg.getMensaje(), msg.getFechaProgramada(), LocalDateTime.now());
                mensajeEnviado = repositorioMensajesEnviados.save(mensajeEnviado);
                repositorioMensajesEnviados.flush();
                logger.info("✅ Mensaje guardado en la tabla mensajes_enviados con ID: {}", mensajeEnviado.getId());

                repositorioMensajesProgramados.delete(msg);
                logger.info("🗑️ Mensaje programado eliminado correctamente.");
            } catch (Exception e) {
                logger.error("❌ Error al enviar mensaje a Discord o guardarlo en la BD: {}", e.getMessage());
            }
        }
    }
}
