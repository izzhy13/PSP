package ejercicios.cuaderno0;

public class Ej04 {
	public static void main(String[] args) throws Exception {
		
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Saludo");
		pb.inheritIO(); // el hijo escribe en mi consola
		Process hijo = pb.start();
		Thread.sleep(1000); //con esto el hijo escribe primero
		
		System.out.println("Padre: he lanzado un hijo con PID " + hijo.pid());
		System.out.println("Padre: yo termino ya");
	}
}
