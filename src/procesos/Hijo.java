package procesos;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso hijo para las recetas de RecetarioPSP.
 * Sin argumentos: eco en mayusculas de lo que recibe por System.in.
 * Con argumentos, segun el primero:
 *   args ...      muestra los argumentos recibidos
 *   entorno       muestra la variable de entorno MODO
 *   dir           muestra su directorio de trabajo
 *   salir N       termina con el codigo N
 *   dormir MS     espera MS milisegundos y termina
 *   mixto         escribe por la salida y por la salida de error
 */
public class Hijo {
	public static void main(String[] args) throws Exception {
		String modo = args.length == 0 ? "eco" : args[0];
		switch (modo) {
		case "args":
			System.out.print("He recibido " + (args.length - 1) + " argumentos:");
			for (int i = 1; i < args.length; i++) {
				System.out.print(" [" + args[i] + "]");
			}
			System.out.println();
			break;
		case "entorno":
			System.out.println("MODO=" + System.getenv("MODO"));
			break;
		case "dir":
			System.out.println("Trabajo en " + System.getProperty("user.dir"));
			break;
		case "salir":
			System.exit(Integer.parseInt(args[1]));
			break;
		case "dormir":
			Thread.sleep(Long.parseLong(args[1]));
			System.out.println("Ya he dormido");
			break;
		case "mixto":
			for (int i = 1; i <= 3; i++) {
				System.out.println("salida " + i);
				System.err.println("error " + i);
			}
			break;
		default:
			eco();
		}
	}

	private static void eco() throws IOException {
		BufferedReader entrada = new BufferedReader(
				new InputStreamReader(System.in));
		String linea;
		int n = 0;
		// Lee hasta que el padre cierre su lado (fin de fichero)
		while ((linea = entrada.readLine()) != null) {
			n++;
			System.out.println("HIJO> " + linea.toUpperCase());
		}
		System.err.println("Hijo: he recibido " + n + " lineas");
	}
}