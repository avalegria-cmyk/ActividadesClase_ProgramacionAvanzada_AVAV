package com.demo.tareas;

import com.demo.tareas.notificaciones.EmailService;
import com.demo.tareas.notificaciones.Notificacion;
import com.demo.tareas.notificaciones.NotificacionesService;
import com.demo.tareas.notificaciones.SMSService;
import com.demo.tareas.notificaciones.WhatsAppService;
import com.demo.tareas.reporte.EmailSender;
import com.demo.tareas.reporte.PdfGenerator;
import com.demo.tareas.reporte.PdfGeneratorImplement;
import com.demo.tareas.reporte.Reporte;
import com.demo.tareas.reporte.ReporteService;
import com.demo.tareas.reporte.SmtpEmailSender;

public class Main {
    public static void main(String[] args) {

        Notificacion email = new EmailService();
        Notificacion whatsapp = new WhatsAppService();
        Notificacion sms = new SMSService();

        NotificacionesService notiEmail = new NotificacionesService(email);
        NotificacionesService notiWhatsapp = new NotificacionesService(whatsapp);
        NotificacionesService notiSMS = new NotificacionesService(sms);

        System.out.println(notiEmail.notificarUsuario("correo@example.com", "Mensaje por Email"));
        System.out.println(notiWhatsapp.notificarUsuario("0963483452", "Mensaje por WhatsApp"));
        System.out.println(notiSMS.notificarUsuario("0963483452", "Mensaje por SMS"));

        // ReporteService con inyección de dependencias
        PdfGenerator pdfGenerator = new PdfGeneratorImplement();
        EmailSender emailSender = new SmtpEmailSender();
        ReporteService reporteService = new ReporteService(pdfGenerator, emailSender);

        Reporte reporte = new Reporte("Reporte Mensual", "Ventas del mes de abril");
        System.out.println(reporteService.generarYEnviar(reporte, "gerente@example.com"));
    }
}
