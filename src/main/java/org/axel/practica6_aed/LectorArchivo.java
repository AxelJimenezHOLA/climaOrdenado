package org.axel.practica6_aed;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que permite leer el archivo .csv
 * Se limita a leer solo el archivo weatherHistory.csv en la carpeta input.
 */
public class LectorArchivo {
    private static final String RUTA_ARCHIVO = "input/weatherHistory.csv";

    /**
     * Crea una lista de reportes de clima y la regresa para ser utilizada.
     */
    public ArrayList<ReporteClima> crearListaPrincipal() {
        ArrayList<ReporteClima> reportes = new ArrayList<>();
        procesarArchivo(linea -> {
            String[] datos = linea.split(",");
            if (!datos[0].equals("Formatted Date")) {
                agregarReporteClima(reportes, datos);
            }
        });
        return reportes;
    }

    /**
     * Agrega un reporte del clima individualmente a la lista.
     * Aquí se hace distinción de cada String leído de la línea, transformando a Double cuando es necesario.
     */
    private void agregarReporteClima(ArrayList<ReporteClima> reportes, String[] datos) {
        ReporteClima reporte = new ReporteClima(
                datos[0],                           // Fecha
                datos[1],                           // Resumen corto
                datos[2],                           // Tipo de precipitación
                Double.parseDouble(datos[3]),       // Temperatura real
                Double.parseDouble(datos[4]),       // Temperatura aparente
                Double.parseDouble(datos[5]) * 100, // Humedad
                Double.parseDouble(datos[6]),       // Velocidad del viento
                Double.parseDouble(datos[7]),       // Rodamiento del viento
                Double.parseDouble(datos[8]),       // Visibilidad
                Double.parseDouble(datos[9]),       // Cubierta ruidosa
                Double.parseDouble(datos[10]),      // Presión
                datos[11]                           // Resumen diario
        );
        reportes.addLast(reporte);
    }

    /**
     * Permite procesar línea por línea del archivo.
     * Implementa una interfaz funcional para que podamos
     * decidir qué hacer con la línea mediante una lambda
     */
    private void procesarArchivo(ProcessLine processor) {
        try (BufferedReader lector = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                processor.process(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: no se encontró el archivo.");
        } catch (IOException e) {
            System.out.println("Error: no se puede leer.");
        }
    }

    /**
     * La interfaz funcional que permite procesar un String según nos convenga.
     */
    @FunctionalInterface
    private interface ProcessLine {
        void process(String line);
    }
}