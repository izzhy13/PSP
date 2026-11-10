package ejercicios.cuaderno0;

import java.io.IOException;

public class Ej03_Yo {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		System.out.println("Padre: mi PID es " + ProcessHandle.current().pid());
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Ej03_MiPadre");
		pb.inheritIO();
		pb.start().waitFor();
	}
}