package org.axel.practica6_aed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class OrdenadorClima {
    private final Ordenador<ReporteClima> climaOrdenador = new Ordenador<>();

    public double ordenarReportesClima(ArrayList<ReporteClima> reportes, OrdenarPor campoOrdenar, TipoOrdenamiento tipo) {
        ReporteClima[] reportesArr = reportes.toArray(new ReporteClima[0]);
        Comparator<ReporteClima> comparador = obtenerComparador(campoOrdenar);
        long startTime;
        long endTime;

        switch (tipo) {
            case Quick -> {
                startTime = System.nanoTime();
                climaOrdenador.hacerQuickSort(reportesArr, 0, reportesArr.length - 1, comparador);
                endTime = System.nanoTime();
            }
            case Merge -> {
                startTime = System.nanoTime();
                climaOrdenador.hacerMergeSort(reportesArr, 0, reportesArr.length - 1, comparador);
                endTime = System.nanoTime();
            }
            case Shell -> {
                startTime = System.nanoTime();
                climaOrdenador.hacerShellSort(reportesArr, comparador);
                endTime = System.nanoTime();
            }
            case Selection -> {
                startTime = System.nanoTime();
                climaOrdenador.hacerSelectionSort(reportesArr, comparador);
                endTime = System.nanoTime();
            }
            case Radix -> {
                int[] subArr = obtenerSubarreglo(reportes, campoOrdenar);
                startTime = System.nanoTime();
                climaOrdenador.hacerRadixSort(subArr);
                reagregarReportesOrdenados(reportes, subArr, campoOrdenar);
                endTime = System.nanoTime();
                return endTime - startTime;
            }
            case Regular -> {
                startTime = System.nanoTime();
                Arrays.sort(reportesArr, comparador);
                endTime = System.nanoTime();
            }
            case Parallel -> {
                startTime = System.nanoTime();
                Arrays.parallelSort(reportesArr, comparador);
                endTime = System.nanoTime();
            }
            default -> throw new IllegalStateException("Valor no esperado: " + tipo);
        }

        reportes.clear();
        reportes.addAll(Arrays.asList(reportesArr));
        return endTime - startTime;
    }


    private void reagregarReportesOrdenados(ArrayList<ReporteClima> reportes, int[] atributosOrdenados,
                                            OrdenarPor campoOrdenar) {
        ArrayList<ReporteClima> reportesOrdenados = new ArrayList<>(reportes.size());

        for (int atributoOrdenado : atributosOrdenados) {
            ReporteClima reporteEncontrado = encontrarYRetirarReporte(reportes, atributoOrdenado, campoOrdenar);
            if (reporteEncontrado != null) {
                reportesOrdenados.add(reporteEncontrado);
            }
        }

        reportes.clear();
        reportes.addAll(reportesOrdenados);
    }

    private ReporteClima encontrarYRetirarReporte(ArrayList<ReporteClima> reportes, int valorAtributo,
                                                  OrdenarPor campoOrdenar) {
        for (int i = 0; i < reportes.size(); i++) {
            ReporteClima reporte = reportes.get(i);
            int valueToCompare = switch (campoOrdenar) {
                case Temperatura_real -> (int) (reporte.getTemperaturaReal() * 100);
                case Temperatura_aparente -> (int) (reporte.getTemperaturaAparente() * 100);
                case Humedad -> (int) (reporte.getHumedad() * 100);
                case Velocidad_del_viento -> (int) (reporte.getVelocidadViento() * 100);
                case Rodamiento_del_viento -> (int) (reporte.getRodamientoViento() * 100);
                case Visibilidad -> (int) (reporte.getVisibilidad() * 100);
                case Presión -> (int) (reporte.getPresion() * 100);
            };

            if (valueToCompare == valorAtributo) {
                return reportes.remove(i);
            }
        }
        return null;
    }

    private int[] obtenerSubarreglo(ArrayList<ReporteClima> reportes, OrdenarPor campoOrdenar) {
        return reportes.stream().mapToInt(reporte -> {
            return switch (campoOrdenar) {
                case Temperatura_real -> (int) (reporte.getTemperaturaReal() * 100);
                case Temperatura_aparente -> (int) (reporte.getTemperaturaAparente() * 100);
                case Humedad -> (int) (reporte.getHumedad() * 100);
                case Velocidad_del_viento -> (int) (reporte.getVelocidadViento() * 100);
                case Rodamiento_del_viento -> (int) (reporte.getRodamientoViento() * 100);
                case Visibilidad -> (int) (reporte.getVisibilidad() * 100);
                case Presión -> (int) (reporte.getPresion() * 100);
                default -> throw new IllegalArgumentException("Tipo no válido");
            };
        }).toArray();
    }

    private Comparator<ReporteClima> obtenerComparador(OrdenarPor campoOrdenar) {
        return switch (campoOrdenar) {
            case Temperatura_real -> Comparator.comparing(ReporteClima::getTemperaturaReal);
            case Temperatura_aparente -> Comparator.comparing(ReporteClima::getTemperaturaAparente);
            case Humedad -> Comparator.comparing(ReporteClima::getHumedad);
            case Velocidad_del_viento -> Comparator.comparing(ReporteClima::getVelocidadViento);
            case Rodamiento_del_viento -> Comparator.comparing(ReporteClima::getRodamientoViento);
            case Visibilidad -> Comparator.comparing(ReporteClima::getVisibilidad);
            case Presión -> Comparator.comparing(ReporteClima::getPresion);
        };
    }
}