package ejercicios.cuaderno0;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Ej22 {
	
	public static void main(String[] args) throws Exception {
		Process hijo = Lanzador.para("ejercicios.cuaderno0.Eco").start();
		
		// aHijo escribe en la entrada del hijo con autoFlush = true (envía cada println ya);
		// deHijo lee la salida del hijo línea a línea. Al salir del try se cierran ambos.
		try (PrintWriter aHijo = new PrintWriter(hijo.outputWriter(), true);
				BufferedReader deHijo = hijo.inputReader()) {
			
			for (String msg : new String[] {"uno", "dos", "FIN"}) {
				aHijo.println(msg); // true: se envía ya
				String respuesta = deHijo.readLine(); // espero la respuesta
				System.out.println(msg + " -> " + respuesta);
			}
		}
		
		// El fin del diálogo lo marcó "FIN"; aquí el hijo ya ha terminado y recogemos su código
		System.out.println("Código: " + hijo.waitFor());
	}
}