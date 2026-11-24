package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class SumaPadreMejorada {
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
        
        String errores;
        try (BufferedReader err = hijo.errorReader()) {
            errores = String.join(" | ", err.lines().toList());
        }
        
        boolean acaboATiempo = hijo.waitFor(5, TimeUnit.SECONDS);
        if (!acaboATiempo) {
            hijo.destroyForcibly();
            hijo.waitFor();
            System.out.println("Padre: el hijo superó el plazo y fue detenido");
            return;
        }
        

        int codigo = hijo.exitValue();
        System.out.println("Padre: código de salida = " + codigo);
        if (!errores.isEmpty()) {
            System.out.println("Padre: errores del hijo -> " + errores);
        }

        String resultado = Files.readString(fichero, StandardCharsets.UTF_8).strip();
        System.out.println("Suma recibida desde el fichero: " + resultado);
    }
}