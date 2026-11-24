package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class DoblePadre {
	public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno1.Doble").start();

        try (PrintWriter alHijo = new PrintWriter(hijo.outputWriter(), true);
             BufferedReader deHijo = hijo.inputReader()) {

            for (String num : new String[]{"3", "8", "15"}) {
                alHijo.println(num);
                String respuesta = deHijo.readLine();
                System.out.println("Padre envía " + num + " -> Hijo contesta " + respuesta);
            }
        } // cierra la entrada del hijo -> el hijo recibe null y termina

        System.out.println("Código de salida: " + hijo.waitFor());
    }
}
