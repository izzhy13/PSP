package ejercicios.cuaderno0;

import java.io.File;

public class Ej10 {
	public static void main(String[] args) throws Exception {
		System.out.println("--- Sin directory()");
		Lanzador.para("ejercicios.cuaderno0.DondeEstoy").inheritIO().start().waitFor();

		System.out.println("--- Con directory(new File(\"datos\"))");
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.DondeEstoy");

		pb.directory(new File("datos")); //new file porque
		pb.inheritIO().start().waitFor();
	}
}
