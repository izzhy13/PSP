package ejercicios.cuaderno0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/** Lee líneas hasta que se acaba la entrada y las repite en mayúsculas. */
public class Mayusculas {
	public static void main(String[] args) throws IOException {
		//cuando un hijo lee texto por su entrada estándar (System.in) línea a línea
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		
		String linea;
		while ((linea = entrada.readLine()) != null) {
			System.out.println(linea.toUpperCase());
		}
		System.out.println("(fin de la entrada)");
	}

}