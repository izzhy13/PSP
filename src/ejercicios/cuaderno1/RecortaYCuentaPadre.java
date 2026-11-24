package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class RecortaYCuentaPadre {
	public static void main(String[] args) throws Exception {
		Process hijo = Lanzador.para("ejercicios.cuaderno1.RecortaYCuenta").start();

		try (BufferedWriter w = hijo.outputWriter()) {
			w.write("  hola  ");
			w.newLine();
			w.write("  java  ");
			w.newLine();
			w.write("  tuberias  ");
			w.newLine();
		} // al salir del try se cierra la tubería -> el hijo recibe null

		try (BufferedReader r = hijo.inputReader()) {
			String respuesta;
			while ((respuesta = r.readLine()) != null) {
				System.out.println("Hijo -> " + respuesta);
			}
		}

		System.out.println("Código: " + hijo.waitFor());
	}
}
