public class SMSService implements Notificacion {
    @Override
    public void enviar(String usuario, String mensaje) {
        System.out.println("Enviando SMS a: " + usuario + ": " + mensaje);
    }
}