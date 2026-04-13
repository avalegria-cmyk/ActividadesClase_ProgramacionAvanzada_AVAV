public class WhatsAppService implements Notificacion {
    @Override
    public void enviar(String usuario, String mensaje) {
        System.out.println("Enviando WHATSAPP a: " + usuario + ": " + mensaje);
    }
}