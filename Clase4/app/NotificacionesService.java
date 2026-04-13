public class NotificacionesService {

    private Notificacion servicio;

    public NotificacionesService(Notificacion servicio) {
        this.servicio = servicio;
    }

    public void notificarUsuario(String usuario, String mensaje) {
        servicio.enviar(usuario, mensaje);
    }
}