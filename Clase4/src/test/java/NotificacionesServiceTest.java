import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionesServiceTest {

    @Test
    void testNotificarUsuarioEmail() {
        Notificacion servicio = new EmailService();
        NotificacionesService noti = new NotificacionesService(servicio);

        noti.notificarUsuario("test@example.com", "Hola");
    }

    @Test
    void testNotificarUsuarioWhatsApp() {
        Notificacion servicio = new WhatsAppService();
        NotificacionesService noti = new NotificacionesService(servicio);

        noti.notificarUsuario("0999999999", "Hola");
    }

    @Test
    void testNotificarUsuarioSMS() {
        Notificacion servicio = new SMSService();
        NotificacionesService noti = new NotificacionesService(servicio);

        noti.notificarUsuario("0988888888", "Hola");
    }
}
