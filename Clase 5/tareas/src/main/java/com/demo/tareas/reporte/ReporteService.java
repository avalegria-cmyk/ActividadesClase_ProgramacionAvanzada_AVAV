package com.demo.tareas.reporte;

public class ReporteService {
    private PdfGenerator pdfGen;
    private EmailSender sender;

    // Inyección de dependencias por constructor
    public ReporteService(PdfGenerator pdfGen, EmailSender sender) {
        this.pdfGen = pdfGen;
        this.sender = sender;
    }

    public String generarYEnviar(Reporte r, String email) {
        byte[] pdf = pdfGen.generar(r);
        return sender.enviar(email, pdf);
    }
}
