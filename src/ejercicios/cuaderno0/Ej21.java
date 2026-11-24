package ejercicios.cuaderno0;

import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;
public class Ej21 {
	public static void main(String[] args) throws Exception {
		Process hijo = Lanzador.para("ejercicios.cuaderno0.Mayusculas")
				.redirectOutput(ProcessBuilder.Redirect.INHERIT)
				.start();
		BufferedWriter w = hijo.outputWriter();
		w.write("hola");
		w.newLine();
		w.flush(); // enviado, pero sin cerrar
		boolean termino = hijo.waitFor(3, TimeUnit.SECONDS);
		System.out.println("Padre: ¿terminó? " + termino);
		w.close(); // ahora sí: fin de la entrada
		System.out.println("Padre: tras cerrar, código " + hijo.waitFor());
	}
}

//inheritIO() hereda los tres flujos, también la entrada, y entonces el padre
//no podría escribir al hijo. Aquí solo se hereda la salida y la entrada sigue 
//conectada al padre por una tubería.