package com.reto.programador_mensajes.repository;

import com.reto.programador_mensajes.model.MensajeProgramado;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMensajesProgramados extends JpaRepository<MensajeProgramado, Long> {
    List<MensajeProgramado> findByFechaProgramadaBefore(LocalDateTime now);
}