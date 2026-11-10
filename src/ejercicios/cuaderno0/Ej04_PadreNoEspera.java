package ejercicios.cuaderno0;

import java.io.IOException;

public class Ej04_PadreNoEspera {

	public static void main(String[] args) throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Ej04_Saludo");
		pb.inheritIO();
		Process hijo = pb.start();
		//Thread.sleep(1000);
		
		System.out.println("Padre: he lanzado a un hijo con el PID " + hijo.pid());
		System.out.println("Padre: yo termino ya");
		
	}

}
