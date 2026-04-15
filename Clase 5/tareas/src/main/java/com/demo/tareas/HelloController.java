package com.demo.tareas;

import com.demo.tareas.notificaciones.EmailService;
import com.demo.tareas.notificaciones.NotificacionesService;
import com.demo.tareas.notificaciones.SMSService;
import com.demo.tareas.notificaciones.WhatsAppService;
import com.demo.tareas.reporte.PdfGeneratorImplement;
import com.demo.tareas.reporte.Reporte;
import com.demo.tareas.reporte.ReporteService;
import com.demo.tareas.reporte.SmtpEmailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hola Mundo";
    }

    @GetMapping("/notificaciones")
    public String notificaciones() {
        NotificacionesService notiEmail = new NotificacionesService(new EmailService());
        NotificacionesService notiWhatsapp = new NotificacionesService(new WhatsAppService());
        NotificacionesService notiSMS = new NotificacionesService(new SMSService());

        return notiEmail.notificarUsuario("correo@example.com", "Mensaje por Email") + "\n"
             + notiWhatsapp.notificarUsuario("0963483452", "Mensaje por WhatsApp") + "\n"
             + notiSMS.notificarUsuario("0963483452", "Mensaje por SMS");
    }

    @GetMapping("/reporte")
    public String reporte() {
        ReporteService reporteService = new ReporteService(new PdfGeneratorImplement(), new SmtpEmailSender());
        Reporte reporte = new Reporte("Reporte Mensual", "Ventas del mes de abril");
        return reporteService.generarYEnviar(reporte, "gerente@example.com");
    }
}