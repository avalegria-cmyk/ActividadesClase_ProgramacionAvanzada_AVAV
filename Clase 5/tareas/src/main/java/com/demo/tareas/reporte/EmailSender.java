package com.demo.tareas.reporte;

public interface EmailSender {
    String enviar(String email, byte[] contenido);
}
