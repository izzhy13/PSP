package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/** Dialoga por tubería validando palabras de 4 o más letras. FIN termina. */
public class ValidadorPalabras {
	public static void main(String[] args) throws IOException {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		PrintWriter salida = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
		PrintWriter errores = new PrintWriter(System.err, true, StandardCharsets.UTF_8);

		String linea;
		while ((linea = entrada.readLine()) != null) {
			String palabra = linea.strip();
			if ("FIN".equals(palabra)) {
				salida.println("OK:CERRANDO");
				System.exit(0);
			}

			if (palabra.length() >= 4) {
				salida.println("VALIDA:" + palabra.toUpperCase());
			} else {
				errores.println("CORTA:" + palabra);
			}
		}
		System.exit(0);
	}
}