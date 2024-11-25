package org.axel.practica6_aed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControladorClima {
    private static final int INTERVALO_DATOS_GRAFICA = 100;
    private final OrdenadorClima ordenadorClima = new OrdenadorClima();
    private final LectorArchivo lectorArchivo = new LectorArchivo();
    private final Map<String, Double> tiemposEjecucion = new HashMap<>();
    // Comboboxes
    @FXML
    private ComboBox<OrdenarPor> campoOrdenarComboBox;
    // Checkboxes
    @FXML
    private CheckBox quickCheckBox;
    @FXML
    private CheckBox mergeCheckBox;
    @FXML
    private CheckBox shellCheckBox;
    @FXML
    private CheckBox selectionCheckBox;
    @FXML
    private CheckBox radixCheckBox;
    @FXML
    private CheckBox sortCheckBox;
    @FXML
    private CheckBox parallelCheckBox;
    // Botones y labels
    @FXML
    private Button ordenarButton;
    @FXML
    private Button randomizarButton;
    @FXML
    private Label tiempoLabel;
    // Tabla
    @FXML
    private TableView<ReporteClima> datosTable;
    @FXML
    private TableColumn<ReporteClima, Double> temperaturaRealColumn = new TableColumn<>("Temperatura");
    @FXML
    private TableColumn<ReporteClima, Double> temperaturaAparenteColumn = new TableColumn<>("Temperatura Aparente");
    @FXML
    private TableColumn<ReporteClima, Double> humedadColumn = new TableColumn<>("Humedad");
    @FXML
    private TableColumn<ReporteClima, Double> velocidadVientoColumn = new TableColumn<>("Velocidad Viento");
    @FXML
    private TableColumn<ReporteClima, Double> rodamientoVientoColumn = new TableColumn<>("Rodamiento Viento");
    @FXML
    private TableColumn<ReporteClima, Double> visibilidadColumn = new TableColumn<>("Visibilidad");
    @FXML
    private TableColumn<ReporteClima, Double> presionColumn = new TableColumn<>("Presión");
    // Gráficas
    @FXML
    private BarChart<String, Number> temperaturaRealChart;
    @FXML
    private BarChart<String, Number> temperaturaAparenteChart;
    @FXML
    private BarChart<String, Number> humedadChart;
    @FXML
    private BarChart<String, Number> velocidadVientoChart;
    @FXML
    private BarChart<String, Number> rodamientoVientoChart;
    @FXML
    private BarChart<String, Number> visibilidadChart;
    @FXML
    private BarChart<String, Number> presionChart;
    @FXML
    private BarChart<String, Number> tiempoEjecucionChart;
    // Más atributos
    private ArrayList<ReporteClima> reportes;

    @FXML
    public void initialize() {
        campoOrdenarComboBox.setItems(FXCollections.observableArrayList(OrdenarPor.values()));
        reportes = lectorArchivo.crearListaPrincipal();

        temperaturaRealColumn.setCellValueFactory(new PropertyValueFactory<>("temperaturaReal"));
        temperaturaAparenteColumn.setCellValueFactory(new PropertyValueFactory<>("temperaturaAparente"));
        humedadColumn.setCellValueFactory(new PropertyValueFactory<>("humedad"));
        velocidadVientoColumn.setCellValueFactory(new PropertyValueFactory<>("velocidadViento"));
        rodamientoVientoColumn.setCellValueFactory(new PropertyValueFactory<>("rodamientoViento"));
        visibilidadColumn.setCellValueFactory(new PropertyValueFactory<>("visibilidad"));
        presionColumn.setCellValueFactory(new PropertyValueFactory<>("presion"));

        ponerDatosEnTabla();
    }

    @FXML
    private void onOrdenarButtonClick() {
        OrdenarPor campoOrdenar = campoOrdenarComboBox.getValue();
        long tiempoInicio = System.nanoTime();

        if (campoOrdenar != null) {
            ArrayList<ReporteClima> reportesOriginales = new ArrayList<>(reportes);
            tiemposEjecucion.clear();
            boolean firstMethodExecuted = false;

            if (quickCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Quick, campoOrdenar, reportesOriginales, !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            if (mergeCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Merge, campoOrdenar, reportesOriginales, !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            if (shellCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Shell, campoOrdenar, reportesOriginales, !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            if (selectionCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Selection, campoOrdenar, reportesOriginales,
                        !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            if (radixCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Radix, campoOrdenar, reportesOriginales, !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            if (sortCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Regular, campoOrdenar, reportesOriginales, !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            if (parallelCheckBox.isSelected()) {
                ejecutarOrdenamiento(TipoOrdenamiento.Parallel, campoOrdenar, reportesOriginales, !firstMethodExecuted);
                firstMethodExecuted = true;
            }
            long tiempoFinal = System.nanoTime();
            double tiempoTotal = (tiempoFinal - tiempoInicio) / 1e6;

            ponerDatosEnTabla();
            actualizarGraficaTiempoEjecucion();
            tiempoLabel.setText("%.2f ms".formatted(tiempoTotal));
        }
    }

    @FXML
    private void onRandomizarButtonClick() {
        Collections.shuffle(reportes);
        ponerDatosEnTabla();
    }

    private void ejecutarOrdenamiento(TipoOrdenamiento tipo, OrdenarPor campoOrdenar,
                                      ArrayList<ReporteClima> reportesOriginales, boolean esElPrimero) {
        ArrayList<ReporteClima> reportesCopia = new ArrayList<>(reportesOriginales);
        long startTime = System.nanoTime();
        ordenadorClima.ordenarReportesClima(reportesCopia, campoOrdenar, tipo);
        long endTime = System.nanoTime();

        double tiempoEjecucion = (endTime - startTime) / 1e6;
        tiemposEjecucion.put("%s".formatted(tipo), tiempoEjecucion);

        if (esElPrimero) {
            reportes = reportesCopia;
        }
    }

    private void actualizarGraficaTiempoEjecucion() {
        tiempoEjecucionChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tiempo de Ejecución");

        for (Map.Entry<String, Double> entry : tiemposEjecucion.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        tiempoEjecucionChart.getData().add(series);
    }

    @FXML
    private void ponerDatosEnTabla() {
        ObservableList<ReporteClima> reportesObservable = FXCollections.observableArrayList(reportes);
        datosTable.setItems(reportesObservable);

        datosTable.getColumns().clear();

        datosTable.getColumns().addAll(
                temperaturaRealColumn,
                temperaturaAparenteColumn,
                humedadColumn,
                velocidadVientoColumn,
                rodamientoVientoColumn,
                visibilidadColumn,
                presionColumn
        );

        actualizarGraficas();
    }

    private void actualizarGraficas() {
        actualizarGrafica(temperaturaRealChart, "Temperatura Real", ReporteClima::getTemperaturaReal);
        actualizarGrafica(temperaturaAparenteChart, "Temperatura Aparente", ReporteClima::getTemperaturaAparente);
        actualizarGrafica(humedadChart, "Humedad", ReporteClima::getHumedad);
        actualizarGrafica(velocidadVientoChart, "Velocidad del Viento", ReporteClima::getVelocidadViento);
        actualizarGrafica(rodamientoVientoChart, "Rodamiento del Viento", ReporteClima::getRodamientoViento);
        actualizarGrafica(visibilidadChart, "Visibilidad", ReporteClima::getVisibilidad);
        actualizarGrafica(presionChart, "Presión", ReporteClima::getPresion);
    }

    private void actualizarGrafica(BarChart<String, Number> chart, String seriesName, ValorReporte valorReporte) {
        chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);

        int step = Math.max(1, reportes.size() / INTERVALO_DATOS_GRAFICA);
        for (int i = 0; i < reportes.size(); i += step) {
            ReporteClima reporte = reportes.get(i);
            series.getData().add(new XYChart.Data<>(String.valueOf(i), valorReporte.obtener(reporte)));
        }

        chart.getData().add(series);
    }

    @FunctionalInterface
    private interface ValorReporte {
        double obtener(ReporteClima reporte);
    }
}