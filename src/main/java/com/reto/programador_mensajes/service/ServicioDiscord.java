package com.reto.programador_mensajes.service;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//Servicio para la integración con Discord.

@Service
public class ServicioDiscord {
    private static final Logger logger = LoggerFactory.getLogger(ServicioDiscord.class);
    private final GatewayDiscordClient cliente;
    private final String idCanal;
    private final Random random = new Random();

    public ServicioDiscord(@Value("${discord.bot.token}") String token, @Value("${discord.channel.id}") String idCanal) {
        this.idCanal = idCanal;
        this.cliente = inicializarCliente(token);
        configurarEscuchaMensajes();
    }

    private GatewayDiscordClient inicializarCliente(String token) {
        try {
            logger.info("🔗 Conectando a Discord...");
            return DiscordClient.create(token).login().block();
        } catch (Exception e) {
            logger.error("❌ Error al conectar con Discord: {}", e.getMessage());
            throw new RuntimeException("No se pudo conectar con Discord. Verifica el token.");
        }
    }

    public void enviarMensajeAlCanal(String mensaje) {
        if (cliente == null) {
            logger.error("❌ Cliente de Discord no inicializado. No se puede enviar el mensaje.");
            return;
        }

        cliente.getChannelById(Snowflake.of(idCanal))
                .ofType(MessageChannel.class)
                .flatMap(canal -> {
                    logger.info("📤 Enviando mensaje a Discord: {}", mensaje);
                    return canal.createMessage(mensaje);
                })
                .doOnError(error -> logger.error("❌ Error enviando mensaje a Discord: {}", error.getMessage()))
                .subscribe();
    }

    private void configurarEscuchaMensajes() {
        if (cliente == null) {
            logger.warn("⚠️ Cliente de Discord no inicializado. No se escucharán mensajes.");
            return;
        }

        cliente.on(MessageCreateEvent.class).subscribe(evento -> {
            Message mensaje = evento.getMessage();
            String contenido = mensaje.getContent().toLowerCase();

            switch (contenido) {
                case "!status" -> responder(mensaje, "🤖 Bot en línea y funcionando.");
                case "!hora" -> responder(mensaje, "🕒 Hora actual: " + LocalDateTime.now());
                case "!ayuda" -> responder(mensaje, obtenerListaComandos());
                case "!motivame" -> responder(mensaje, obtenerFraseMotivacional());
                case "!chiste" -> responder(mensaje, contarChiste());
                case "!dado" -> responder(mensaje, "🎲 Has lanzado un dado y salió: " + (random.nextInt(6) + 1));
                case "!coinflip" -> responder(mensaje, lanzarMoneda());
                default -> {
                    if (contenido.startsWith("!di ")) {
                        responder(mensaje, contenido.substring(4));
                    }
                }
            }
        });
    }

    private String obtenerListaComandos() {
        return """
                **📜 Lista de comandos disponibles:**
                🔹 `!status` - Ver el estado del bot.
                🔹 `!hora` - Obtener la hora actual.
                🔹 `!motivame` - Recibir una frase motivacional.
                🔹 `!chiste` - Escuchar un chiste gracioso.
                🔹 `!dado` - Lanzar un dado de 6 caras.
                🔹 `!coinflip` - Lanzar una moneda (cara o cruz).
                🔹 `!di [mensaje]` - El bot repetirá lo que escribas.
                """;
    }

    private String obtenerFraseMotivacional() {
        List<String> frases = List.of(
                "💪 No importa lo lento que vayas, siempre y cuando no te detengas.",
                "🚀 Cada día es una nueva oportunidad para cambiar tu vida.",
                "🔥 El éxito es la suma de pequeños esfuerzos repetidos día tras día.",
                "🌟 Cree en ti mismo y en todo lo que eres. ¡Eres capaz de lograr grandes cosas!",
                "🏆 Los desafíos son lo que hacen la vida interesante; superarlos es lo que le da sentido.",
                "💡 El único modo de hacer un gran trabajo es amar lo que haces.",
                "🌈 No dejes que el miedo te impida alcanzar tus sueños.",
                "📚 El conocimiento te da poder, pero el carácter te da respeto.",
                "💥 No esperes el momento perfecto, toma el momento y hazlo perfecto.",
                "🎯 La única forma de hacer algo grandioso es creer que puedes hacerlo."
        );
        return frases.get(random.nextInt(frases.size()));
    }

    private String contarChiste() {
        List<String> chistes = List.of(
                "😂 ¿Por qué el libro de matemáticas estaba triste? Porque tenía demasiados problemas.",
                "🤣 ¿Qué hace una abeja en el gimnasio? ¡Zum-ba!",
                "😆 ¿Cuál es el animal más antiguo? La cebra, porque está en blanco y negro.",
                "😜 ¿Qué le dice un gusano a otro gusano? Voy a dar una vuelta a la manzana.",
                "😂 ¿Cómo se despiden los químicos? Ácido un placer.",
                "🤣 Mamá, en el colegio me llaman Facebook. ¿Y tú qué les dices? ¡Me gusta!",
                "😆 ¿Qué le dice un jardinero a otro? ¡Disfrutemos mientras podamos!",
                "😜 ¿Qué hace un pez en el cine? Nada.",
                "😂 ¿Cuál es el café más peligroso del mundo? El ex-preso.",
                "🤣 ¿Qué le dice el 0 al 8? ¡Bonito cinturón!"
        );
        return chistes.get(random.nextInt(chistes.size()));
    }

    private String lanzarMoneda() {
        return random.nextBoolean() ? "🪙 Ha salido **CARA**." : "🪙 Ha salido **CRUZ**.";
    }

    private void responder(Message mensaje, String respuesta) {
        mensaje.getChannel()
                .flatMap(canal -> canal.createMessage(respuesta))
                .subscribe();
    }
}
