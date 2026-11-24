package ejercicios.cuaderno0;

import java.io.File;

public class Ej19Fichero {
	//Cuando la salida es grande, quieres conservarla o hay varios hijos.
	
	public static void main(String[] args) throws Exception {
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Cuenta", "100000");
		pb.redirectOutput(new File("salida100k.txt"));

		Process hijo = pb.start();
		int codigo = hijo.waitFor(); // espera a que termine de escribir el fichero
		System.out.println("Código: " + codigo);
	}
}
