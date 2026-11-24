package ejercicios.cuaderno0;

import java.io.BufferedReader;

public class Ej17 {
	public static void main(String[] args) throws Exception {
		Process hijo = Lanzador.para("ejercicios.cuaderno0.Cuenta", "4").start();
		//Process hijo = Lanzador.para("ejercicios.cuaderno0.Duerme", "2").start(); //no recibe todo al final
		int n = 0;
		
		try (BufferedReader r = hijo.inputReader()) { //el try cierra el lector al terminar
			String linea;
			while ((linea = r.readLine()) != null) { // null = fin
				n++;
				System.out.println("Padre recibe (" + n + "): " + linea);
			}
		}
		System.out.println("Código: " + hijo.waitFor());
	}
}
