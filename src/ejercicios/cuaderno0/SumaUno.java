package ejercicios.cuaderno0;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Suma 1 al número de contador.txt, tantas veces como se indique. */
public class SumaUno {
	public static void main(String[] args) throws IOException {
		// Número de incrementos que debe realizar este proceso hijo
		int veces = Integer.parseInt(args[0]);
		Path fichero = Path.of("contador.txt");
		
		for (int i = 0; i < veces; i++) {
			// 1. Leer el valor actual del fichero
			String texto = Files.readString(fichero).strip();
			// 2. Convierte ese texto a número (int valor), o usa 0 si el texto está vacío
			int valor = texto.isEmpty() ? 0 : Integer.parseInt(texto);
			// 3. Escribe en contador.txt el resultado de valor + 1 convertido a texto
			Files.writeString(fichero, String.valueOf(valor + 1));
		}
	}
}
