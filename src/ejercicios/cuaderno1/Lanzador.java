	package ejercicios.cuaderno1;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Prepara la orden para ejecutar otra clase de este mismo proyecto. */

public final class Lanzador {
	
	private Lanzador() {
	}
	
	public static ProcessBuilder para(String clase, String... argumentos) {
		
		//java.home es la carpeta del JDK qye esra ejecutando al padre
		String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
		String cp = Path.of(System.getProperty("java.class.path")).toAbsolutePath().toString(); //donde estan mis clases
		
		List<String> orden = new ArrayList<>();
		orden.add(java);
		orden.add("-cp");
		orden.add(cp);
		orden.add(clase);
		orden.addAll(List.of(argumentos));
		
		//System.out.println(orden);
		return new ProcessBuilder(orden);
	}
}