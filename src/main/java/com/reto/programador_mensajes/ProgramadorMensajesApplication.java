package com.reto.programador_mensajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProgramadorMensajesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramadorMensajesApplication.class, args);
	}
}
