package ejercicios.cuaderno0;

public class Ej01_MiPID {

	public static void main(String[] args) throws InterruptedException {
	
		// ProcessHandle.current() devuelve un objeto que representa al proceso que está ejecutando este código
		long pid = ProcessHandle.current().pid();
		System.out.println("Soy un proceso y mi PID es " + pid);
		Thread.sleep(20_000);
		System.out.println("Termino despues de esperar");	
		
	}

}
