package ejercicios.cuaderno0;

public class Ej16 {
	public static void main(String[] args) throws Exception {
		Process hijo = Lanzador.para("ejercicios.cuaderno0.Duerme", "3").start();
		//hijo.exitValue(); error de IllegalThreadStateException: process hasn't exited
		ProcessHandle.Info info = hijo.info();
		
		System.out.println("PID: " + hijo.pid());
		System.out.println("¿Vivo? " + hijo.isAlive());
		System.out.println("Orden: " + info.command().orElse("?"));
		System.out.println("Usuario: " + info.user().orElse("?"));
		System.out.println("Inicio: " + info.startInstant().orElse(null));
		hijo.waitFor();
		System.out.println("¿Vivo? " + hijo.isAlive());
		System.out.println("Código: " + hijo.exitValue());
	}
}

//Usa exitValue() solo después de waitFor(), o tras comprobar que isAlive() es false