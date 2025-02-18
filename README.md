# 🤖 **Programador de Mensajes para Discord**  🤖
Automatización de mensajes en un servidor de **Discord**, permitiendo programar y enviar mensajes automáticamente en una fecha y hora establecida.
---
# 👀 **Mira lo que se puede hacer**

<img src="https://github.com/user-attachments/assets/86a930fe-39b6-4998-b4b0-28889fea43f7" alt="Descripción de la imagen" width="600"/>



---

## 📌 **Características**
✅ **API REST** para programar, consultar y eliminar mensajes.  
✅ **Base de datos MySQL** para almacenar mensajes programados y enviados.  
✅ **Envío automático** de mensajes al canal de Discord configurado.  
✅ **Documentación de la API** con **Swagger/OpenAPI**.  
✅ **Interfaz web** con HTML, CSS y JavaScript, ejecutable con **Live Server**.  

---

## 🛠 **Tecnologías Utilizadas**

| **Backend**                                       | **Frontend**                                       |
|--------------------------------------------------|---------------------------------------------------|
| <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/> | <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/> |
| <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/> | <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white"/> |
| <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> | <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"/> |
| <img src="https://img.shields.io/badge/Discord4J-7289DA?style=for-the-badge&logo=discord&logoColor=white"/> | <img src="https://img.shields.io/badge/LiveServer-02569B?style=for-the-badge&logo=visualstudiocode&logoColor=white"/> |

---

## 📂 **Estructura del Proyecto**
📦 programador-mensajes  
┣ 📂 src  
┃ ┣ 📂 main  
┃ ┃ ┣ 📂 java/com/reto/programador_mensajes  
┃ ┃ ┃ ┣ 📂 config # Configuración (Swagger, CORS)  
┃ ┃ ┃ ┣ 📂 controller # Controladores REST  
┃ ┃ ┃ ┣ 📂 model # Modelos de datos (Mensajes)  
┃ ┃ ┃ ┣ 📂 repository # Repositorios JPA  
┃ ┃ ┃ ┣ 📂 service # Lógica de negocio  
┃ ┃ ┃ ┣ ProgramadorMensajesApplication.java  
┃ ┃ ┣ 📂 resources  
┃ ┃ ┃ ┣ application.properties # Configuración de la BD y bot de Discord  
┣ 📂 frontend  
┃ ┣ index.html # Interfaz web  
┃ ┣ styles.css # Estilos  
┃ ┣ script.js # Lógica frontend  
┣ README.md  
┣ pom.xml # Dependencias Maven

---

# 📥 **Instalación**

## 1. **Clonar el Repositorio**
Clona el repositorio de GitHub en tu máquina local:

```bash
https://github.com/DeisonBM/programador_mensajes.git
```

## 2. **Configuración del Backend**
Para configurar el backend, sigue estos pasos:

### Requisitos Previos
- **Java 11 o superior**: Asegúrate de tener instalado Java en tu máquina.
- **Maven**: El proyecto utiliza Maven para gestionar dependencias.
- **MySQL**: Necesitarás tener MySQL instalado y funcionando en tu máquina.

### Configuración de la Base de Datos
1. Crea una base de datos en MySQL llamada `programador_mensajes`.
2. Modifica el archivo `src/main/resources/application.properties` con la configuración de tu base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/programador_mensajes
spring.datasource.username=tu-usuario
spring.datasource.password=tu-contraseña
spring.jpa.hibernate.ddl-auto=update
```

### Configuración del Bot de Discord
1. **Crear un Bot en Discord:**
   - Dirígete al [Portal de Desarrolladores de Discord](https://discord.com/developers/applications).
   - Crea una nueva aplicación y ve a la sección de "Bot".
   - Copia el **Token del Bot**.

2. **Obtener el ID del Canal:**
   - Asegúrate de tener habilitado el "Modo de Desarrollador" en Discord.
   - Haz clic derecho sobre el canal donde el bot enviará los mensajes y selecciona "Copiar ID".

3. **Configurar el Token y el ID del Canal:**
   - Abre el archivo `src/main/resources/application.properties`.
   - Añade el **Token del Bot** y el **ID del Canal** en el archivo:

```properties
discord.bot.token=tu-token-de-bot
discord.channel.id=tu-id-del-canal
```

### Ejecutar el Backend
Desde el directorio raíz del proyecto, compila y ejecuta el proyecto con Maven:

```bash
mvn spring-boot:run
```

El servidor se iniciará en `http://localhost:8080`.

## 🚀 **Ejecutar el Proyecto Completo**
1. Ejecuta el **Backend** como se mencionó en los pasos anteriores.
2. Ejecuta el **Frontend** con Live Server.
3. El bot de Discord debería empezar a funcionar automáticamente y estar listo para enviar mensajes programados en el servidor de Discord.

---

## 📩 **Endpoints de la API**
### 📤 **Mensajes Programados**
| Método   | Endpoint             | Descripción                  |
|----------|----------------------|------------------------------|
| `POST`   | `/api/message`       | Programar un mensaje.       |
| `GET`    | `/api/messages`      | Obtener mensajes programados. |
| `DELETE` | `/api/message/{id}`  | Eliminar un mensaje programado. |

### 📬 **Mensajes Enviados**
| Método | Endpoint             | Descripción                  |
|--------|----------------------|------------------------------|
| `GET`  | `/api/sent-messages` | Obtener mensajes enviados.   |

---

## 🤖 **Comandos del Bot de Discord**

| **Comando**         | **Descripción**                             |
|---------------------|---------------------------------------------|
| `!status`           | Ver el estado del bot.                      |
| `!hora`             | Obtener la hora actual.                    |
| `!motivame`         | Recibir una frase motivacional.            |
| `!chiste`           | Escuchar un chiste gracioso.               |
| `!dado`             | Lanzar un dado de 6 caras.                 |
| `!coinflip`         | Lanzar una moneda (cara o cruz).           |
| `!di [mensaje]`     | El bot repetirá lo que escribas.           |

---

## ⚙️ **Dependencias Utilizadas**
### 🖥 **Backend**
- **Spring Boot** (Web, JPA, Scheduling)
- **MySQL Driver**
- **Discord4J** (Integración con Discord)
- **SpringDoc OpenAPI** (Swagger)
- **Lombok** (Reducir código repetitivo)

### 🎨 **Frontend**
- **HTML, CSS y JavaScript**
- **Fetch API** para consumir la API
- **Live Server** para ejecutar localmente

---

## 👨‍💻 **Autor**
📌 **Deison Jose Barreto Montes**  
🔗 [LinkedIn](https://www.linkedin.com/in/deison-barreto-montes/) | 🐱 [GitHub](https://github.com/DeisonBM)  

---

## ⚡ **Notas Finales**
✅ **Asegúrate de que tu bot de Discord tenga permisos para enviar mensajes en el canal.**  
✅ **Puedes modificar los estilos en `styles.css` para personalizar la interfaz.**  
✅ **Si tienes problemas con CORS, verifica la configuración en `ConfiguracionWeb.java`.**  
✅ **Live Server: Puedes utilizar Live Server para ejecutar el frontend localmente, pero asegúrate de que la API esté funcionando antes de probarlo.**

## 🤝 Contribuir

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz un commit (`git commit -m 'Agregar nueva funcionalidad'`).
4. Sube los cambios a tu fork (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## 📄 Licencia

Este proyecto está licenciado bajo la **Licencia MIT**. 

