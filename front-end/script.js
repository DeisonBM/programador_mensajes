const urlApiMensajes = "http://localhost:8080/api/messages";  // Obtener mensajes programados
const urlGuardarMensaje = "http://localhost:8080/api/message";  // Guardar un nuevo mensaje
const urlMensajesEnviados = "http://localhost:8080/api/sent-messages";  // Obtener mensajes enviados
const urlEliminarMensaje = "http://localhost:8080/api/message";  // Eliminar mensaje programado

// Función para formatear la fecha
function formatearFecha(fechaCadena) {
    if (!fechaCadena) return "Fecha no disponible";

    const fecha = new Date(fechaCadena);
    const opciones = { year: 'numeric', month: '2-digit', day: '2-digit' };
    const fechaFormateada = fecha.toLocaleDateString('es-ES', opciones);
    
    let horas = fecha.getHours();
    let minutos = fecha.getMinutes();
    const ampm = horas >= 12 ? 'PM' : 'AM';

    horas = horas % 12;
    horas = horas ? horas : 12; 
    minutos = minutos < 10 ? '0' + minutos : minutos;

    return `${fechaFormateada} Hora ${horas}:${minutos} ${ampm}`;
}

// Verificar la conexión con la API cada 5 segundos
function verificarConexion() {
    fetch(urlApiMensajes)
        .then(respuesta => {
            const estadoConexion = document.getElementById("estado-conexion");
            if (respuesta.ok) {
                estadoConexion.textContent = "Estado con el API: ✅ Conectado";
                estadoConexion.className = "conectado";
            } else {
                estadoConexion.textContent = "❌ Desconectado";
                estadoConexion.className = "desconectado";
            }
        })
        .catch(() => {
            const estadoConexion = document.getElementById("estado-conexion");
            estadoConexion.textContent = "❌ Desconectado";
            estadoConexion.className = "desconectado";
        });
}

// Ejecutar al cargar la página y actualizar cada 5 segundos
document.addEventListener("DOMContentLoaded", function() {
    verificarConexion();
    setInterval(verificarConexion, 5000);
    cargarMensajes();
    cargarMensajesEnviados();  
});

// Función para obtener mensajes programados
function cargarMensajes() {
    fetch(urlApiMensajes)
        .then(respuesta => respuesta.json())
        .then(mensajes => {
            const tabla = document.getElementById("tablaMensajes");
            tabla.innerHTML = "";

            mensajes.forEach(mensaje => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${mensaje.id}</td>
                    <td>${mensaje.mensaje}</td>
                    <td>${formatearFecha(mensaje.fechaProgramada)}</td>
                    <td><button onclick="eliminarMensaje(${mensaje.id})">❌ Eliminar</button></td>
                `;
                tabla.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al obtener los mensajes programados:", error));
}

// Función para obtener mensajes enviados
function cargarMensajesEnviados() {
    fetch(urlMensajesEnviados)
        .then(respuesta => respuesta.json())
        .then(mensajes => {
            const tabla = document.getElementById("tablaMensajesEnviados");
            tabla.innerHTML = "";

            mensajes.forEach(mensaje => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${mensaje.id}</td>
                    <td>${mensaje.mensaje}</td>
                    <td>${formatearFecha(mensaje.fechaProgramada)}</td>
                    <td>${formatearFecha(mensaje.fechaEnviada)}</td>
                `;
                tabla.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al obtener los mensajes enviados:", error));
}

// Función para agregar un nuevo mensaje
document.getElementById("formularioMensaje").addEventListener("submit", function(evento) {
    evento.preventDefault();

    const mensaje = document.getElementById("mensaje").value;
    const horaProgramada = document.getElementById("horaProgramada").value;

    if (!mensaje || !horaProgramada) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    const nuevoMensaje = { mensaje: mensaje, fechaProgramada: horaProgramada };

    fetch(urlGuardarMensaje, {  
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(nuevoMensaje)
    })
    .then(respuesta => {
        if (!respuesta.ok) throw new Error("Error al guardar mensaje");
        return respuesta.json();
    })
    .then(() => {
        console.log("✅ Mensaje guardado correctamente");
        cargarMensajes(); 
        document.getElementById("formularioMensaje").reset(); 
    })
    .catch(error => console.error("Error al agregar mensaje:", error));
});

// Función para eliminar un mensaje programado
function eliminarMensaje(id) {
    fetch(`${urlEliminarMensaje}/${id}`, { method: "DELETE" })
        .then(respuesta => {
            if (!respuesta.ok) {
                if (respuesta.status === 404) {
                    throw new Error(`Mensaje con ID ${id} no encontrado`);
                }
                throw new Error("Error desconocido al eliminar");
            }
            return respuesta.text();
        })
        .then(() => {
            console.log(`✅ Mensaje ${id} eliminado con éxito`);
            cargarMensajes(); 
        })
        .catch(error => console.error("Error al eliminar:", error.message));
}