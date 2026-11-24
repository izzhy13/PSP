package ejercicios.cuaderno0;

import java.io.BufferedReader;

public class Ej19Leer {
	//Cuando el padre necesita usar o analizar lo que responde el hijo.

	public static void main(String[] args) throws Exception {
		long t0 = System.currentTimeMillis();
		Process hijo = Lanzador.para("ejercicios.cuaderno0.Cuenta", "100000").start();
		long lineas = 0;
		
		try (BufferedReader r = hijo.inputReader()) {
			lineas = r.lines().count(); // leer vacía la tubería
		}
		
		int codigo = hijo.waitFor(); // ahora sí termina
		long ms = System.currentTimeMillis() - t0;
		System.out.println("Líneas: " + lineas + ", código " + codigo + ", " + ms + " ms");
	}
}

//CUATRO FORMAS DE QUE EL HIJO NO SE BLOQUEE
//Leer la salida antes de waitFor(), como aquí: cuando el padre necesita lo que dice el hijo.
//Redirigir a un fichero con redirectOutput (ejercicio 12): cuando la salida es grande o se quiere conservar.
//Descartarla con redirectOutput(ProcessBuilder.Redirect.DISCARD): cuando no interesa en absoluto.
//Heredarla con inheritIO() (ejercicio 4): cuando solo quieres verla en la consola.

