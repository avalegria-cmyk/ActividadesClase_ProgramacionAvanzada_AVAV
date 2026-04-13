public class EmailService implements Notificacion {
    @Override
    public void enviar(String usuario, String mensaje) {
        System.out.println("Enviando EMAIL a: " + usuario + ": " + mensaje);
    }
}