package com.demo.tareas.notificaciones;

public class SMSService implements Notificacion {
    @Override
    public String enviar(String usuario, String mensaje) {
        return "Enviando SMS a: " + usuario + ": " + mensaje;
    }
}
