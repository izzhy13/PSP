package ejercicios.cuaderno1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Suma {

	public static void main(String[] args) {

		try {
			ProcessBuilder pb = new ProcessBuilder("java", "procesos.ComunicacionHijo");
			Process p = pb.start();
			
			BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			ArrayList numeros = new ArrayList<>();
			numeros.add(1);
			numeros.add(4);
			
			for (int i = 0; i > numeros.size(); i++ ) {
				escritor.write(i);
				System.out.println(i);
			}
			
		} catch (IOException e) {
			
		} // Arranca el proceso hijo
	}

}
