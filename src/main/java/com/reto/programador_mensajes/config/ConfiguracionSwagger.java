package com.reto.programador_mensajes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para la documentación de la API.
 */
@Configuration
public class ConfiguracionSwagger {

    @Bean
    public OpenAPI apiDocumentada() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Programación de Mensajes para Discord")
                        .description("Documentación de los endpoints de la API")
                        .version("1.0"));
    }
}
