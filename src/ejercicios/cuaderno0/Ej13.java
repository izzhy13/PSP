package ejercicios.cuaderno0;

import java.nio.charset.StandardCharsets;

public class Ej13 {
	
	public static void main(String[] args) throws Exception {
		
		// el padre lee la salida del hijo con getInputStream()
		Process hijo = Lanzador.para("ejercicios.cuaderno0.Saludo").start();
		//Process hijo = Lanzador.para("ejercicios.cuaderno0.Cuenta","5").start();
		byte[] bytes = hijo.getInputStream().readAllBytes(); // hasta el final
		// readAllBytes() no devuelve el control hasta que se acaba el flujo.
		String texto = new String(bytes, StandardCharsets.UTF_8).strip(); //strip para el salto de linea
		int codigo = hijo.waitFor();
		System.out.println("Padre: el hijo dijo «" + texto + "»");
		System.out.println("Padre: y terminó con código " + codigo);
	}
}