package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SumaPadre {
    public static void main(String[] args) throws Exception {
        Path fichero = Path.of("suma.txt");
        Files.deleteIfExists(fichero);

        Process hijo = Lanzador.para("ejercicios.cuaderno1.Sumador").start();

        // Enviamos los números por la tubería de entrada y cerramos al salir del try
        // para que el hijo sepa que no llegan más datos (readLine() -> null)
        try (BufferedWriter w = hijo.outputWriter()) {
            String[] numeros = { "10", "25", "15", "50" };
            for (String n : numeros) {
                w.write(n);
                w.newLine();
            }
        }

        int codigo = hijo.waitFor();
        if (codigo != 0) {
            System.out.println("El hijo terminó con error. Código: " + codigo);
            return;
        }

        String resultado = Files.readString(fichero, StandardCharsets.UTF_8).strip();
        System.out.println("Suma recibida desde el fichero: " + resultado);
    }
}