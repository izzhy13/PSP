package ejercicios.cuaderno0;

import java.io.IOException;

public class Ej19Discard {
	//Cuando la salida no interesa en absoluto y quieres rapidez

	public static void main(String[] args) throws Exception {
//		Process p = Lanzador.para("ejercicios.cuaderno0.Cuenta", "100000")
//	            .redirectOutput(ProcessBuilder.Redirect.DISCARD)
//	            .start();
		
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Cuenta", "100000");
		pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);

		Process hijo = pb.start();
		int codigo = hijo.waitFor(); // ya no se bloquea
		System.out.println("Código: " + codigo);
	}

}
