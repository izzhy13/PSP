package ejercicios.cuaderno0;

import java.io.BufferedReader;

public class Ej23 {
	public static void main(String[] args) throws Exception {
		System.out.println("--- Solo getInputStream, sin unir");
		Process a = Lanzador.para("ejercicios.cuaderno0.Mixto").start();

		try (BufferedReader r = a.inputReader()) {
			r.lines().forEach(l -> System.out.println("leído: " + l));
		}

		a.waitFor();

		System.out.println("--- Con redirectErrorStream(true)");
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Mixto");
		pb.redirectErrorStream(true);
		Process b = pb.start();

		//para que aparezcan juntos
		try (BufferedReader r = b.inputReader()) {
			r.lines().forEach(l -> System.out.println("leído: " + l));
		}

		System.out.println("--- Con errorReader ---f");
		//para leer aparte la salida de errores
		try (BufferedReader err = a.errorReader()) {
			err.lines().forEach(l -> System.out.println("error: " + l));
		}

		b.waitFor();
	}
}

//LAS OPCIONES PARA LOS ERRORES
//redirectErrorStream(true): unidos a la salida normal; el padre lee un solo flujo.
//redirectError(new File("errores.txt")): a un fichero propio.
//redirectError(ProcessBuilder.Redirect.INHERIT): a la consola del padre, para verlos.
//Por defecto, a una tubería que el padre debe leer con errorReader() o getErrorStream().