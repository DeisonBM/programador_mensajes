package com.reto.programador_mensajes.repository;

import com.reto.programador_mensajes.model.MensajeEnviado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMensajesEnviados extends JpaRepository<MensajeEnviado, Long> {
}
