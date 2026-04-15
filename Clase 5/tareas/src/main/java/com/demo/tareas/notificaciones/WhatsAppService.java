package com.demo.tareas.notificaciones;

public class WhatsAppService implements Notificacion {
    @Override
    public String enviar(String usuario, String mensaje) {
        return "Enviando WHATSAPP a: " + usuario + ": " + mensaje;
    }
}
