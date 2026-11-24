package ejercicios.cuaderno1;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SumaPadreScanner { // Lee números por consola con Scanner y se los envía al hijo usando Lanzador

	public static void main(String[] args) throws Exception {
		Path fichero = Path.of("suma.txt"); // Fichero donde el hijo guardará la suma final[cite: 48]
		Files.deleteIfExists(fichero); // Borra el fichero previo si existía para no leer un resultado antiguo[cite: 48, 49]

		// Prepara la orden para lanzar Sumador usando Lanzador y arranca el proceso hijo[cite: 7, 8, 14]
		Process hijo = Lanzador.para("ejercicios.cuaderno1.Sumador").start();

		// Scanner lee lo que escribes en la terminal/consola del padre;
		// BufferedWriter envía cada número por la tubería de entrada hacia el hijo[cite: 38]
		try (Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
				BufferedWriter w = hijo.outputWriter()) {

			System.out.println("Escribe números (uno por línea). Escribe 'fin' para terminar:");

			while (sc.hasNextLine()) {
				String entrada = sc.nextLine().strip(); // Lee la línea introducida y limpia espacios/saltos[cite: 50]

				if (entrada.equalsIgnoreCase("fin")) { // Palabra acordada para dejar de enviar números
					break;
				}

				if (!entrada.isEmpty()) {
					Long.parseLong(entrada); // Verifica que el texto introducido sea un número entero válido
					w.write(entrada); // Escribe el número en la entrada estándar del hijo
					w.newLine(); // Añade salto de línea para que el hijo lo lea con readLine()
					w.flush(); // Envía el número de inmediato por la tubería sin esperar a llenar el búfer
				}
			}
		} // Al salir del try se cierra 'w'; el hijo recibe null en readLine(), escribe suma.txt y termina

		int codigo = hijo.waitFor(); // Espera a que el hijo termine y obtiene su código de salida

		if (codigo == 0) { 
			String total = Files.readString(fichero, StandardCharsets.UTF_8).strip(); // Lee el resultado guardado
			System.out.println("La suma guardada en suma.txt es: " + total);
		} else {
			System.out.println("El hijo falló con código: " + codigo); 
		}
	}
}