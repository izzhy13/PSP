package ejercicios.cuaderno0;

import java.io.IOException;

public class Ej19Inherit {
	//Cuando solo quieres verla en consola mientras depuras o ejecutas

	public static void main(String[] args) throws Exception {
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Cuenta", "100000");
		pb.inheritIO();

		Process hijo = pb.start();
		int codigo = hijo.waitFor(); // no se bloquea porque la salida va a la consola
		System.out.println("Código: " + codigo);
	}

}
