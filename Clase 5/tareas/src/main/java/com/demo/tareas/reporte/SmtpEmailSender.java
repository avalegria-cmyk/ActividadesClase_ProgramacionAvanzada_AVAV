package com.demo.tareas.reporte;

public class SmtpEmailSender implements EmailSender {
    @Override
    public String enviar(String email, byte[] contenido) {
        return "Enviando PDF por SMTP a: " + email + " (" + contenido.length + " bytes)";
    }
}
