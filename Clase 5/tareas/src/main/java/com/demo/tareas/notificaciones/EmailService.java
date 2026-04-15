package com.demo.tareas.notificaciones;

public class EmailService implements Notificacion {
    @Override
    public String enviar(String usuario, String mensaje) {
        return "Enviando EMAIL a: " + usuario + ": " + mensaje;
    }
}
