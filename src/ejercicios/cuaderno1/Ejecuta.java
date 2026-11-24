package ejercicios.cuaderno1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ejecuta {

    private static final int EXIT_NOT_FOUND = 127;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java ejercicios.cuaderno1; <programa> [argumentos...]");
            System.exit(EXIT_NOT_FOUND);
        }

        // Construimos la lista de comando y argumentos preservando cada elemento tal cual llega
        List<String> command = new ArrayList<>(Arrays.asList(args));

        ProcessBuilder pb = new ProcessBuilder(command);
        // Hereda entrada, salida y error estándar de la consola actual
        pb.inheritIO();

        try {
            Process process = pb.start();

            // Mostrar el PID antes de esperar a que termine el proceso hijo
            System.out.println("[Ejecuta] Proceso lanzado con PID: " + process.pid());

            int exitCode = process.waitFor();

            System.out.println("[Ejecuta] Proceso finalizado con código de salida: " + exitCode);
            System.exit(exitCode);

        } catch (IOException e) {
            System.err.println("[Ejecuta] Error al lanzar '" + args[0] + "': " + e.getMessage());
            System.err.println("[Ejecuta] Código de salida: " + EXIT_NOT_FOUND);
            System.exit(EXIT_NOT_FOUND);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Ejecuta] La espera del proceso fue interrumpida: " + e.getMessage());
            System.exit(1);
        }
    }
}