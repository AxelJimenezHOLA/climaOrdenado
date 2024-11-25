package org.axel.practica6_aed;

/**
 * Un reporte del clima representa como atributos todas las líneas del archivo .csv del clima.
 * Realmente solo se toman en cuenta los valores numéricos para el ordenamiento.
 */
public class ReporteClima {
    private final String fecha;
    private final String resumenCorto;
    private final String tipoPrecipitacion;
    private final double temperaturaReal;
    private final double temperaturaAparente;
    private final double humedad;
    private final double velocidadViento;
    private final double rodamientoViento;
    private final double visibilidad;
    private final double cubiertaRuidosa;
    private final double presion;
    private final String resumenDiario;

    /**
     * Constructor que recibe todos los atributos como parámetros.
     */
    public ReporteClima(String fecha, String resumenCorto, String tipoPrecipitacion, double temperaturaReal,
                        double temperaturaAparente, double humedad, double velocidadViento, double rodamientoViento,
                        double visibilidad, double cubiertaRuidosa, double presion, String resumenDiario) {
        this.fecha = fecha;
        this.resumenCorto = resumenCorto;
        this.tipoPrecipitacion = tipoPrecipitacion;
        this.temperaturaReal = temperaturaReal;
        this.temperaturaAparente = temperaturaAparente;
        this.humedad = humedad;
        this.velocidadViento = velocidadViento;
        this.rodamientoViento = rodamientoViento;
        this.visibilidad = visibilidad;
        this.cubiertaRuidosa = cubiertaRuidosa;
        this.presion = presion;
        this.resumenDiario = resumenDiario;
    }

    // Getters y setters de los atributos
    public String getFecha() {
        return fecha;
    }

    public String getResumenCorto() {
        return resumenCorto;
    }

    public String getTipoPrecipitacion() {
        return tipoPrecipitacion;
    }

    public double getTemperaturaReal() {
        return temperaturaReal;
    }

    public double getTemperaturaAparente() {
        return temperaturaAparente;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getVelocidadViento() {
        return velocidadViento;
    }

    public double getRodamientoViento() {
        return rodamientoViento;
    }

    public double getVisibilidad() {
        return visibilidad;
    }

    public double getCubiertaRuidosa() {
        return cubiertaRuidosa;
    }

    public double getPresion() {
        return presion;
    }

    public String getResumenDiario() {
        return resumenDiario;
    }
}