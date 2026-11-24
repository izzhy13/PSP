package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ValidadorPadre {
	public static void main(String[] args) throws Exception {

		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno1.ValidadorPalabras");
		pb.redirectErrorStream(true); // junta salida normal y errores en un solo tub
		
		Process hijo = pb.start();
		String[] envios = {"sol", "proceso", "ok", "tuberia", "FIN"};

		try (PrintWriter alHijo = new PrintWriter(hijo.outputWriter(), true);
				BufferedReader deHijo = hijo.inputReader()) {

			for (String palabra : envios) {
		        alHijo.println(palabra);               // 1. Le mando la palabra al hijo
		        String respuesta = deHijo.readLine(); // 2. Leo lo que me conteste (sea ok o error)
		        System.out.println(palabra + " -> " + respuesta);
		    }
		}

		int codigo = hijo.waitFor();
		System.out.println("Código de salida: " + codigo);
	}
}
