package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/** Lee hasta fin de flujo (null) y muestra cada texto recortado con su longitud. */
public class RecortaYCuenta {
	public static void main(String[] args) throws IOException {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		PrintWriter salida = new PrintWriter(System.out, true, StandardCharsets.UTF_8);

		String linea;
		while ((linea = entrada.readLine()) != null) {
			String limpia = linea.strip();
			if (!limpia.isEmpty()) {
				salida.println(limpia + " (" + limpia.length() + ")");
			}
		}
	}
}