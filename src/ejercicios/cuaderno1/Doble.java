package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Doble {
	public static void main(String[] args) throws IOException {

		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		PrintWriter salida = new PrintWriter(System.out, true, StandardCharsets.UTF_8);

		String linea;
		while ((linea = entrada.readLine()) != null) {
			int n = Integer.parseInt(linea.strip());
			salida.println(n * 2);
		}
	}
}
