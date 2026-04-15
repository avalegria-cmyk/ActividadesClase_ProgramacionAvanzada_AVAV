package com.demo.tareas.notificaciones;

public class NotificacionesService {

    private Notificacion servicio;

    public NotificacionesService(Notificacion servicio) {
        this.servicio = servicio;
    }

    public String notificarUsuario(String usuario, String mensaje) {
        return servicio.enviar(usuario, mensaje);
    }
}
