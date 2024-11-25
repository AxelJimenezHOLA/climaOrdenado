package org.axel.practica6_aed;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Ordenador<T> {
    public void hacerQuickSort(T[] arreglo, int inicio, int fin, Comparator<T> comparador) {
        try {
            if (inicio < fin) {
                int pivotIdx = particionar(arreglo, inicio, fin, comparador);
                hacerQuickSort(arreglo, inicio, pivotIdx - 1, comparador);
                hacerQuickSort(arreglo, pivotIdx + 1, fin, comparador);
            }
        } catch (StackOverflowError e) {
            mostrarAlerta("QuickSort ha excedido la profundidad de recursión permitida.");
        } catch (Exception e) {
            mostrarAlerta("Error inesperado en QuickSort: " + e.getMessage());
        }
    }

    private int particionar(T[] arreglo, int inicio, int fin, Comparator<T> comparador) {
        T pivot = arreglo[fin];
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (comparador.compare(arreglo[j], pivot) <= 0) {
                i++;
                intercambiar(arreglo, i, j);
            }
        }

        intercambiar(arreglo, i + 1, fin);
        return i + 1;
    }

    public void hacerMergeSort(T[] arreglo, int inicio, int fin, Comparator<T> comparador) {
        try {
            if (inicio < fin) {
                int medio = inicio + (fin - inicio) / 2;
                hacerMergeSort(arreglo, inicio, medio, comparador);
                hacerMergeSort(arreglo, medio + 1, fin, comparador);
                mergeAuxiliar(arreglo, inicio, medio, fin, comparador);
            }
        } catch (StackOverflowError e) {
            mostrarAlerta("MergeSort ha excedido la profundidad de recursión permitida.");
        } catch (OutOfMemoryError e) {
            mostrarAlerta("MergeSort ha agotado la memoria disponible.");
        } catch (Exception e) {
            mostrarAlerta("Error inesperado en MergeSort: " + e.getMessage());
        }
    }

    private void mergeAuxiliar(T[] arreglo, int inicio, int medio, int fin, Comparator<T> comparador) {
        int n1 = medio - inicio + 1;
        int n2 = fin - medio;

        T[] izquierda = (T[]) new Object[n1];
        T[] derecha = (T[]) new Object[n2];

        for (int i = 0; i < n1; i++) {
            izquierda[i] = arreglo[inicio + i];
        }
        for (int j = 0; j < n2; j++) {
            derecha[j] = arreglo[medio + 1 + j];
        }

        int i = 0, j = 0, k = inicio;

        while (i < n1 && j < n2) {
            if (comparador.compare(izquierda[i], derecha[j]) <= 0) {
                arreglo[k] = izquierda[i];
                i++;
            } else {
                arreglo[k] = derecha[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arreglo[k] = izquierda[i];
            i++;
            k++;
        }

        while (j < n2) {
            arreglo[k] = derecha[j];
            j++;
            k++;
        }
    }

    public void hacerShellSort(T[] arreglo, Comparator<T> comparator) {
        try {
            int n = arreglo.length;
            for (int brecha = n / 2; brecha > 0; brecha /= 2) {
                for (int i = brecha; i < n; i++) {
                    T temp = arreglo[i];
                    int j = i;
                    while (j >= brecha && comparator.compare(arreglo[j - brecha], temp) > 0) {
                        arreglo[j] = arreglo[j - brecha];
                        j -= brecha;
                    }
                    arreglo[j] = temp;
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error inesperado en ShellSort: " + e.getMessage());
        }
    }

    public void hacerSelectionSort(T[] arreglo, Comparator<T> comparador) {
        try {
            int n = arreglo.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    if (comparador.compare(arreglo[j], arreglo[minIdx]) < 0) {
                        minIdx = j;
                    }
                }
                if (minIdx != i) {
                    intercambiar(arreglo, i, minIdx);
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error inesperado en SelectionSort: " + e.getMessage());
        }
    }

    public void hacerRadixSort(int[] arreglo) {
        try {
            if (arreglo == null || arreglo.length == 0) return;

            // Separar números positivos y negativos
            List<Integer> negativos = new ArrayList<>();
            List<Integer> positivos = new ArrayList<>();
            for (int num : arreglo) {
                if (num < 0) {
                    negativos.add(-num); // Guardamos el valor absoluto
                } else {
                    positivos.add(num);
                }
            }

            // Ordenar números negativos (usando sus valores absolutos)
            int[] negArray = negativos.stream().mapToInt(Integer::intValue).toArray();
            sortPositive(negArray);

            // Ordenar números positivos
            int[] posArray = positivos.stream().mapToInt(Integer::intValue).toArray();
            sortPositive(posArray);

            // Combinar los resultados
            int index = 0;
            // Primero los negativos (en orden descendente)
            for (int i = negArray.length - 1; i >= 0; i--) {
                arreglo[index++] = -negArray[i];
            }
            // Luego los positivos (en orden ascendente)
            for (int num : posArray) {
                arreglo[index++] = num;
            }
        } catch (OutOfMemoryError e) {
            mostrarAlerta("RadixSort ha agotado la memoria disponible.");
        } catch (Exception e) {
            mostrarAlerta("Error inesperado en RadixSort: " + e.getMessage());
        }
    }

    private void sortPositive(int[] arr) {
        if (arr.length == 0) return;
        int max = Arrays.stream(arr).max().getAsInt();

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    private void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int j : arr) {
            count[(j / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    private void intercambiar(T[] arreglo, int i, int j) {
        T temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    private void mostrarAlerta(String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de ordenamiento");
            alert.setHeaderText("Se produjo un error durante el ordenamiento");
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
}