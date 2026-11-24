package ejercicios.cuaderno0;

public class Ej15 {
	public static void main(String[] args) throws Exception {
		System.out.println("--- destroy()");
		Process a = Lanzador.para("ejercicios.cuaderno0.Terco").inheritIO().start();
		Thread.sleep(1500); // le damos tiempo a arrancar
		a.destroy(); //SIGTERM 15
		System.out.println("Código: " + a.waitFor());
		
		System.out.println("--- destroyForcibly()");
		Process b = Lanzador.para("ejercicios.cuaderno0.Terco").inheritIO().start();
		Thread.sleep(1500);
		b.destroyForcibly(); //SIGKILL 9
		System.out.println("Código: " + b.waitFor());
		
		System.out.println(a.supportsNormalTermination());
// forma de saber desde el programa si destroy() dejará limpiar al hijo.
	}
}

// primero salida ordenada, dar un plazo razonable y recurrir a la fuerza solo si no hay respuesta