package com.demo.tareas;

import com.demo.tareas.notificaciones.EmailService;
import com.demo.tareas.notificaciones.NotificacionesService;
import com.demo.tareas.notificaciones.SMSService;
import com.demo.tareas.notificaciones.WhatsAppService;
import com.demo.tareas.reporte.PdfGeneratorImplement;
import com.demo.tareas.reporte.Reporte;
import com.demo.tareas.reporte.ReporteService;
import com.demo.tareas.reporte.SmtpEmailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "index";
    }

    @GetMapping("/notificaciones")
    public String notificaciones(Model model) {
        NotificacionesService notiEmail    = new NotificacionesService(new EmailService());
        NotificacionesService notiWhatsapp = new NotificacionesService(new WhatsAppService());
        NotificacionesService notiSMS      = new NotificacionesService(new SMSService());

        List<String> resultados = List.of(
            notiEmail.notificarUsuario("correo@example.com", "Mensaje por Email"),
            notiWhatsapp.notificarUsuario("0963483452", "Mensaje por WhatsApp"),
            notiSMS.notificarUsuario("0963483452", "Mensaje por SMS")
        );

        model.addAttribute("resultados", resultados);
        return "notificaciones";
    }

    @GetMapping("/reporte")
    public String reporte(Model model) {
        ReporteService reporteService = new ReporteService(new PdfGeneratorImplement(), new SmtpEmailSender());
        Reporte reporte = new Reporte("Reporte Mensual", "Ventas del mes de abril");
        String resultado = reporteService.generarYEnviar(reporte, "gerente@example.com");

        model.addAttribute("resultado", resultado);
        model.addAttribute("titulo", reporte.getTitulo());
        model.addAttribute("descripcion", reporte.getContenido());
        return "reporte";
    }
}