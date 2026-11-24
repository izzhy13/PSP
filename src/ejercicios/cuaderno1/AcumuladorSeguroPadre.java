package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class AcumuladorSeguroPadre {
	public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno1.AcumuladorSeguro").start();

        try (PrintWriter alHijo = new PrintWriter(hijo.outputWriter(), true);
             BufferedReader deHijo = hijo.inputReader()) {

            for (String orden : new String[]{"+25", "-40", "-100", "+10"}) {
                if (!hijo.isAlive()) {
                    System.out.println("Padre: el hijo ya terminó, no envío más órdenes");
                    break;
                }
                
                alHijo.println(orden);
                String resp = deHijo.readLine();
                
                if (resp == null) {
                    System.out.println("Padre: el hijo cerró su salida antes de responder a [" + orden + "]");
                    break;
                }
                
                System.out.println("Orden [" + orden + "] -> " + resp);
            }
        }

        boolean acabo = hijo.waitFor(3, TimeUnit.SECONDS);
        if (!acabo) {
        	System.out.println("Padre: tarda demasiado, pido cierre ordenado...");
            hijo.destroy();
            
            if (!hijo.waitFor(1, TimeUnit.SECONDS)) {
                System.out.println("Padre: no responde, fuerzo cierre");
                hijo.destroyForcibly();
                hijo.waitFor();
            }
            
            System.out.println("Padre: proceso detenido");
            return;
        }

        System.out.println("Padre: código de salida = " + hijo.exitValue());
    }
}
