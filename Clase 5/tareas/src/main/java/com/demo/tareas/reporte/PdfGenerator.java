package com.demo.tareas.reporte;

public interface PdfGenerator {
    byte[] generar(Reporte reporte);
}
