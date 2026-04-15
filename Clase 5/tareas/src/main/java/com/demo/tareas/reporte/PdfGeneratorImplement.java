package com.demo.tareas.reporte;

public class PdfGeneratorImplement implements PdfGenerator {
    @Override
    public byte[] generar(Reporte reporte) {
        String contenidoPdf = "PDF: [" + reporte.getTitulo() + "] " + reporte.getContenido();
        return contenidoPdf.getBytes();
    }
}
