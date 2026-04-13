public class Main {
    public static void main(String[] args) {

        Notificacion email = new EmailService();
        Notificacion whatsapp = new WhatsAppService();
        Notificacion sms = new SMSService();

        NotificacionesService notiEmail = new NotificacionesService(email);
        NotificacionesService notiWhatsapp = new NotificacionesService(whatsapp);
        NotificacionesService notiSMS = new NotificacionesService(sms);
        System.err.println("Enviando notificaciones...");
        notiEmail.notificarUsuario("correo@example.com", "Mensaje por Email");
        notiWhatsapp.notificarUsuario("0963483452", "Mensaje por WhatsApp");
        notiSMS.notificarUsuario("0963483452", "Mensaje por SMS");
    }
}
