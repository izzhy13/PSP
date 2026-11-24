package ejercicios.cuaderno0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/** Responde a cada línea en cuanto la recibe. FIN termina. */
public class Eco {

	public static void main(String[] args) throws IOException {
		// Lector de texto UTF-8 sobre la entrada estándar del hijo (lo que envía el padre)
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		// Escritor sobre System.out con autoFlush = true: vacía el búfer en cada println
		PrintWriter salida = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
		
		String linea;
		// Lee línea a línea hasta que el padre cierre la entrada (null) o el hijo salga
		while ((linea = entrada.readLine()) != null) {
			if (linea.equals("FIN")) {
				salida.println("ADIOS");
				return;
			}
			// Responde inmediatamente a cada línea recibida siguiendo el protocolo
			salida.println("Has dicho: " + linea);
		}
	}
}