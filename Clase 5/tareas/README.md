# Tareas — Spring Boot + Thymeleaf

Aplicación web construida con **Spring Boot** y **Thymeleaf** que demuestra dos patrones de diseño orientados a objetos: **Strategy** en el módulo de notificaciones y **Dependency Injection por constructor** en el módulo de reportes.

---

## Estructura del proyecto

```
src/main/java/com/demo/tareas/
├── HelloController.java          ← Controlador principal (rutas HTTP)
├── notificaciones/
│   ├── Notificacion.java         ← Interfaz Strategy
│   ├── NotificacionesService.java← Servicio que usa la interfaz
│   ├── EmailService.java         ← Implementación: Email
│   ├── SMSService.java           ← Implementación: SMS
│   └── WhatsAppService.java      ← Implementación: WhatsApp
└── reporte/
    ├── PdfGenerator.java         ← Interfaz para generar PDF
    ├── EmailSender.java          ← Interfaz para enviar email
    ├── Reporte.java              ← Modelo de datos del reporte
    ├── PdfGeneratorImplement.java← Implementación: genera PDF simulado
    ├── SmtpEmailSender.java      ← Implementación: envía por SMTP simulado
    └── ReporteService.java       ← Servicio que combina ambas interfaces
```

---

## Módulo de Notificaciones

### Patrón: Strategy

El patrón Strategy permite intercambiar algoritmos (en este caso, canales de envío) en tiempo de ejecución sin cambiar el código del servicio que los usa.

#### 1. Interfaz `Notificacion`

```java
public interface Notificacion {
    String enviar(String usuario, String mensaje);
}
```

Define el **contrato**: cualquier canal de notificación debe implementar el método `enviar()` y devolver un `String` con el resultado.

#### 2. Implementaciones concretas

Cada clase implementa la interfaz con su lógica propia:

```java
// EmailService.java
public String enviar(String usuario, String mensaje) {
    return "Enviando EMAIL a: " + usuario + ": " + mensaje;
}

// SMSService.java
public String enviar(String usuario, String mensaje) {
    return "Enviando SMS a: " + usuario + ": " + mensaje;
}

// WhatsAppService.java
public String enviar(String usuario, String mensaje) {
    return "Enviando WHATSAPP a: " + usuario + ": " + mensaje;
}
```

#### 3. Servicio `NotificacionesService`

Recibe cualquier implementación de `Notificacion` por constructor. No sabe ni le importa cuál es — solo llama a `enviar()`.

```java
public class NotificacionesService {
    private Notificacion servicio;  // puede ser Email, SMS o WhatsApp

    public NotificacionesService(Notificacion servicio) {
        this.servicio = servicio;   // ← inyección por constructor
    }

    public String notificarUsuario(String usuario, String mensaje) {
        return servicio.enviar(usuario, mensaje);  // delega al strategy
    }
}
```

---

## Módulo de Reporte

### Patrón: Inyección de dependencias por constructor

El servicio `ReporteService` necesita dos colaboradores (generador de PDF y enviador de email). En lugar de crearlos internamente, los recibe desde afuera → fácil de testear y de cambiar implementaciones.

#### 1. Interfaces

```java
// PdfGenerator.java
public interface PdfGenerator {
    byte[] generar(Reporte reporte);
}

// EmailSender.java
public interface EmailSender {
    String enviar(String email, byte[] contenido);
}
```

#### 2. Implementaciones concretas

```java
// PdfGeneratorImplement.java
public byte[] generar(Reporte reporte) {
    String contenidoPdf = "PDF: [" + reporte.getTitulo() + "] " + reporte.getContenido();
    return contenidoPdf.getBytes();  // simula un PDF como array de bytes
}

// SmtpEmailSender.java
public String enviar(String email, byte[] contenido) {
    return "Enviando PDF por SMTP a: " + email + " (" + contenido.length + " bytes)";
}
```

#### 3. Modelo `Reporte`

```java
public class Reporte {
    private String titulo;
    private String contenido;
    // constructor + getters
}
```

#### 4. Servicio `ReporteService`

```java
public class ReporteService {
    private PdfGenerator pdfGen;
    private EmailSender sender;

    public ReporteService(PdfGenerator pdfGen, EmailSender sender) {
        this.pdfGen = pdfGen;   // ← inyectado
        this.sender = sender;   // ← inyectado
    }

    public String generarYEnviar(Reporte r, String email) {
        byte[] pdf = pdfGen.generar(r);       // paso 1: genera el PDF
        return sender.enviar(email, pdf);     // paso 2: lo envía
    }
}
```

---

## Controlador principal — `HelloController`

El controlador conecta la lógica Java con las vistas HTML. Recibe peticiones HTTP, ejecuta los servicios y pasa los resultados a Thymeleaf mediante el objeto `Model`.

```java
@Controller
public class HelloController {

    // GET / → vista index.html (panel principal)
    @GetMapping("/")
    public String hello() {
        return "index";
    }

    // GET /notificaciones → ejecuta los 3 canales y pasa resultados
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

        model.addAttribute("resultados", resultados);  // → disponible en HTML como ${resultados}
        return "notificaciones";
    }

    // GET /reporte → genera y "envía" el reporte, pasa datos al HTML
    @GetMapping("/reporte")
    public String reporte(Model model) {
        ReporteService reporteService = new ReporteService(
            new PdfGeneratorImplement(),
            new SmtpEmailSender()
        );
        Reporte reporte = new Reporte("Reporte Mensual", "Ventas del mes de abril");
        String resultado = reporteService.generarYEnviar(reporte, "gerente@example.com");

        model.addAttribute("resultado", resultado);              // → ${resultado}
        model.addAttribute("titulo", reporte.getTitulo());       // → ${titulo}
        model.addAttribute("descripcion", reporte.getContenido()); // → ${descripcion}
        return "reporte";
    }
}
```

---

## Integración con Thymeleaf (HTML)

Thymeleaf es el motor de plantillas que procesa los HTML en el servidor, reemplazando las expresiones `${...}` con los datos del `Model` antes de enviarlos al navegador.

### `notificaciones.html` — cómo recibe y usa los datos Java

```html
<!-- Thymeleaf inyecta el resultado[0] como atributo data-msg del botón -->
<button class="noti-btn email"
        th:attr="data-msg=${resultados[0]}, data-tipo='email'"
        onclick="dispararToast(this)">
    Enviar Email
</button>
```

| Expresión Thymeleaf | Qué valor tiene en tiempo de ejecución |
|---------------------|----------------------------------------|
| `${resultados[0]}`  | `"Enviando EMAIL a: correo@example.com: Mensaje por Email"` |
| `${resultados[1]}`  | `"Enviando WHATSAPP a: 0963483452: Mensaje por WhatsApp"` |
| `${resultados[2]}`  | `"Enviando SMS a: 0963483452: Mensaje por SMS"` |

### `reporte.html` — cómo recibe y usa los datos Java

```html
<!-- Título y descripción del objeto Reporte -->
<h1 th:text="${titulo}">Título del reporte</h1>
<p  th:text="${descripcion}">Descripción</p>

<!-- Resultado del SmtpEmailSender inyectado en el botón -->
<button class="rep-btn enviar"
        th:attr="data-msg=${resultado}, data-tipo='enviar'"
        onclick="dispararToast(this)">
    Enviar por SMTP
</button>
```

| Expresión Thymeleaf | Qué valor tiene en tiempo de ejecución |
|---------------------|----------------------------------------|
| `${titulo}`         | `"Reporte Mensual"` |
| `${descripcion}`    | `"Ventas del mes de abril"` |
| `${resultado}`      | `"Enviando PDF por SMTP a: gerente@example.com (N bytes)"` |

---

## Lógica JavaScript — sistema de notificaciones toast

Una vez que Thymeleaf renderiza el HTML con los datos reales, el JavaScript del cliente maneja la interacción del usuario.

### Flujo al hacer clic en un botón

```
Usuario hace clic
       ↓
dispararToast(el)           ← lee data-msg y data-tipo del botón
       ↓
Busca tema en TEMAS{}       ← color gradiente, label, icono SVG
       ↓
Crea <div class="toast-noti"> con CSS variables --tn-from y --tn-to
       ↓
Inserta en #contenedor-notis (position: fixed, top-right)
       ↓
Auto-cierre a los 3.5s      ← agrega clase .hide → animación salida
```

### Estructura del toast generado

```html
<div class="toast-noti" style="--tn-from:#2563eb; --tn-to:#0ea5e9">
    <button class="tn-close">×</button>
    <div class="tn-icon"><!-- SVG icono --></div>
    <div class="tn-body">
        <div class="tn-title">Email enviado</div>
        <div class="tn-msg">Enviando EMAIL a: correo@example.com: Mensaje por Email</div>
    </div>
</div>
```

---

## Flujo completo de una petición

```
Navegador GET /notificaciones
         ↓
HelloController.notificaciones()
         ↓
Crea 3x NotificacionesService con distinto Strategy
         ↓
Llama notificarUsuario() → cada implementación devuelve su String
         ↓
model.addAttribute("resultados", List.of(...))
         ↓
Thymeleaf procesa notificaciones.html
Reemplaza ${resultados[0..2]} en los atributos data-msg de los botones
         ↓
HTML final enviado al navegador
         ↓
Usuario hace clic → JavaScript lee data-msg → muestra toast top-right
```

---

## Rutas disponibles

| Ruta | Descripción |
|------|-------------|
| `GET /` | Panel principal de acciones |
| `GET /notificaciones` | Ejecuta Email + WhatsApp + SMS y muestra resultados |
| `GET /reporte` | Genera PDF simulado y lo "envía" por SMTP |
