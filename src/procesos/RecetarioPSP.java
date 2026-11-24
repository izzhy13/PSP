package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import ejercicios.cuaderno0.Lanzador;

/**
 * Recetario de comunicacion entre procesos (PSP).
 * Cada receta es un metodo independiente: copia el que necesites.
 * Los hijos se lanzan con Lanzador.para(...); la clase Hijo de este
 * paquete hace de proceso hijo en todas las pruebas del main.
 *
 * Recuerda: la API de procesos solo da flujos de java.io y pide File,
 * no Path. getOutputStream() es la ENTRADA del hijo.
 *
 * Version sin lambdas ni hilos. Donde la forma moderna es mas corta,
 * se deja comentada debajo.
 */
public class RecetarioPSP {

	private static final String HIJO = Hijo.class.getName();

	// Lee todas las lineas que quedan en un BufferedReader.
	// Lo usan varias recetas para recoger la salida del hijo.
	static List<String> leerTodas(BufferedReader br) throws IOException {
		List<String> lineas = new ArrayList<>();
		String linea;
		while ((linea = br.readLine()) != null) {
			lineas.add(linea);
		}
		return lineas;
		// Con lambdas (Java 16): return br.lines().toList();
	}

	// =================================================================
	// LANZAR Y PASAR DATOS AL ARRANCAR
	// =================================================================

	// 1. Lanzar y que el hijo escriba en mi consola
	static int lanzarEnConsola() throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para(HIJO, "dir");
		pb.inheritIO();                     // salida, error Y teclado del padre
		Process hijo = pb.start();
		return hijo.waitFor();              // sin esto, el padre no espera
	}

	// 2. Pasar argumentos: cada uno por separado, sin comillas
	static void pasarArgumentos() throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para(HIJO, "args", "uno", "dos con espacios");
		pb.inheritIO();
		pb.start().waitFor();
	}

//	// 3. Variables de entorno (el hijo las lee con System.getenv)
//	static void pasarEntorno() throws IOException, InterruptedException {
//		ProcessBuilder pb = Lanzador.para(HIJO, "entorno");
//		pb.environment().put("MODO", "pruebas");
//		pb.inheritIO();
//		pb.start().waitFor();
//	}

	// 4. Directorio de trabajo: directory() solo admite File
	static void cambiarDirectorio(Path carpeta)
			throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para(HIJO, "dir");
		pb.directory(carpeta.toFile());
		pb.inheritIO();
		pb.start().waitFor();
	}

	// =================================================================
	// LEER DEL HIJO
	// =================================================================

	// 5. Leer toda la salida, con el error mezclado (evita un bloqueo)
	static List<String> leerSalida(ProcessBuilder pb)
			throws IOException, InterruptedException {
		pb.redirectErrorStream(true);
		Process hijo = pb.start();
		List<String> salida;
		try (BufferedReader br = hijo.inputReader()) {   // leer ANTES de waitFor
			salida = leerTodas(br);
		}
		hijo.waitFor();
		return salida;
	}

	// 6. Leer la salida y el error por separado, sin hilos:
	//    el error va a un fichero y se lee cuando el hijo ha terminado
	static void leerSalidaYError(ProcessBuilder pb, Path ficheroError)
			throws IOException, InterruptedException {
		pb.redirectError(ficheroError.toFile());
		Process hijo = pb.start();
		try (BufferedReader out = hijo.inputReader()) {
			for (String linea : leerTodas(out)) {
				System.out.println("  [stdout] " + linea);
			}
		}
		hijo.waitFor();
		for (String linea : Files.readAllLines(ficheroError)) {
			System.out.println("  [stderr] " + linea);
		}
		// Con hilos (bloque II): leer el error en otro hilo mientras este lee
		// la salida, para verlos a la vez y sin fichero intermedio.
		// Thread lector = Thread.ofVirtual().start(() -> {
		//     try (BufferedReader err = hijo.errorReader()) {
		//         err.lines().forEach(l -> System.out.println("[stderr] " + l));
		//     } catch (IOException e) {
		//         e.printStackTrace();
		//     }
		// });
		// ... leer hijo.inputReader() aqui ...
		// lector.join();
	}

	// 7. Leer la salida de un hijo que no escribe en la codificacion nativa
	//    (orden de cmd en Windows con tildes: Cp850; casi todo en Linux: UTF-8)
	static List<String> leerConCodificacion(ProcessBuilder pb, Charset cs)
			throws IOException, InterruptedException {
		pb.redirectErrorStream(true);
		Process hijo = pb.start();
		List<String> salida;
		try (BufferedReader br = hijo.inputReader(cs)) {
			salida = leerTodas(br);
		}
		hijo.waitFor();
		return salida;
	}

	// =================================================================
	// ESCRIBIR AL HIJO
	// =================================================================

	// 8. Enviar todo de golpe, cerrar y recoger la respuesta
	static List<String> enviarYLeer(List<String> lineas)
			throws IOException, InterruptedException {
		Process hijo = Lanzador.para(HIJO).redirectErrorStream(true).start();
		List<String> salida;
		try (PrintWriter haciaHijo = new PrintWriter(hijo.outputWriter());
				BufferedReader desdeHijo = hijo.inputReader()) {
			for (String linea : lineas) {
				haciaHijo.println(linea);
			}
			haciaHijo.flush();
			hijo.getOutputStream().close(); // fin de fichero: el hijo sale de su bucle
			salida = leerTodas(desdeHijo);
		}
		hijo.waitFor();
		return salida;
	}

	// 9. Conversacion pregunta-respuesta: autoflush y una lectura por envio
	static void conversar(List<String> mensajes)
			throws IOException, InterruptedException {
		Process hijo = Lanzador.para(HIJO).start();
		try (PrintWriter haciaHijo = new PrintWriter(hijo.outputWriter(), true);
				BufferedReader desdeHijo = hijo.inputReader()) {
			for (String m : mensajes) {
				haciaHijo.println(m);       // autoflush: sale ya
				System.out.println("  Padre recibe: " + desdeHijo.readLine());
			}
		}                                   // al cerrar, el hijo recibe el fin
		hijo.waitFor();
	}

	// =================================================================
	// REDIRECCIONES Y TUBERIAS
	// =================================================================

	// 10. El hijo lee de un fichero y escribe en otro (sin tuberia: no se bloquea)
	static int conFicheros(Path entrada, Path salida)
			throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para(HIJO);
		pb.redirectInput(entrada.toFile());
		pb.redirectOutput(salida.toFile());
		pb.redirectError(ProcessBuilder.Redirect.appendTo(
				salida.resolveSibling("errores.log").toFile()));
		return pb.start().waitFor();
	}

	// 11. Tirar la salida que no interesa (y evitar que llene la tuberia)
	static int descartarSalida(ProcessBuilder pb)
			throws IOException, InterruptedException {
		pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
		pb.redirectError(ProcessBuilder.Redirect.DISCARD);
		return pb.start().waitFor();
	}

	// 12. Tuberia a | b (Java 9). La primera etapa puede leer de fichero;
	//     las intermedias no admiten redirecciones
	static List<String> tuberia(Path entrada) throws IOException, InterruptedException {
		ProcessBuilder primera = Lanzador.para(HIJO);
		primera.redirectInput(entrada.toFile());
		ProcessBuilder segunda = Lanzador.para(HIJO);

		List<Process> etapas = ProcessBuilder.startPipeline(List.of(primera, segunda));
		Process ultima = etapas.get(etapas.size() - 1);
		List<String> salida;
		try (BufferedReader br = ultima.inputReader()) {
			salida = leerTodas(br);
		}
		ultima.waitFor();
		return salida;
	}

	// 13. Orden del sistema que necesita el interprete (echo, dir, |, >, *...)
	static ProcessBuilder ordenShell(String orden) {
		if (System.getProperty("os.name").startsWith("Windows")) {
			return new ProcessBuilder("cmd", "/c", orden);
		} else {
			return new ProcessBuilder("sh", "-c", orden);
		}
	}

	// =================================================================
	// CONTROL: CODIGO DE SALIDA, PLAZOS Y FINAL
	// =================================================================

	// 14. Codigo de salida: 0 es exito; el resto lo decide el hijo
	static int codigoDeSalida(int codigo) throws IOException, InterruptedException {
		Process hijo = Lanzador.para(HIJO, "salir", String.valueOf(codigo)).start();
		return hijo.waitFor();
	}

	// 15. Esperar con plazo y, si no termina, pedirle que pare o matarlo
	static void esperarConPlazo(long msHijo, long segundos)
			throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para(HIJO, "dormir", String.valueOf(msHijo));
		pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
		Process hijo = pb.start();
		if (hijo.waitFor(segundos, TimeUnit.SECONDS)) {
			System.out.println("  Termino a tiempo, codigo " + hijo.exitValue());
		} else {
			hijo.destroy();                 // pide terminar (en Linux, codigo 143)
			if (!hijo.waitFor(2, TimeUnit.SECONDS)) {
				hijo.destroyForcibly();     // si no hace caso, se mata
			}
			System.out.println("  No termino a tiempo: destruido, codigo "
					+ hijo.waitFor());
		}
	}

	// 16. Seguir trabajando mientras el hijo corre, y comprobar de vez en
	//     cuando si ha terminado (isAlive no bloquea; exitValue si lo
	//     llamas antes de tiempo lanza IllegalThreadStateException)
	static void trabajarMientras(long msHijo) throws IOException, InterruptedException {
		ProcessBuilder pb = Lanzador.para(HIJO, "dormir", String.valueOf(msHijo));
		pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
		Process hijo = pb.start();
		System.out.println("  Lanzado el PID " + hijo.pid() + "; el padre sigue");
		int vueltas = 0;
		while (hijo.isAlive()) {
			vueltas++;                      // aqui iria el trabajo del padre
			hijo.waitFor(100, TimeUnit.MILLISECONDS);   // pausa corta
		}
		System.out.println("  El PID " + hijo.pid() + " ha terminado con codigo "
				+ hijo.exitValue() + " (el padre dio " + vueltas + " vueltas)");
		// Con lambdas (Java 9): que el aviso llegue solo, sin preguntar.
		// hijo.onExit().thenAccept(p ->
		//         System.out.println("Termino con codigo " + p.exitValue()));
	}

	// =================================================================
	// COMUNICARSE POR FICHERO
	// =================================================================

	// 17. Escribir para otro proceso sin que lea un fichero a medias:
	//     se escribe en un temporal y se renombra al final (atomico en el mismo disco)
	static void publicar(Path destino, List<String> lineas) throws IOException {
		Path temporal = destino.resolveSibling(destino.getFileName() + ".tmp");
		Files.write(temporal, lineas);
		Files.move(temporal, destino, StandardCopyOption.ATOMIC_MOVE,
				StandardCopyOption.REPLACE_EXISTING);
	}

	// =================================================================
	// PRUEBA DE TODAS LAS RECETAS
	// =================================================================
	public static void main(String[] args) throws Exception {
		Path dir = Files.createTempDirectory("recetario-psp");

		System.out.println("1. Consola heredada, codigo " + lanzarEnConsola());
		System.out.println("2. Argumentos:");
		pasarArgumentos();
		
//		System.out.println("3. Entorno:");
//		pasarEntorno();
		
		System.out.println("4. Directorio:");
		cambiarDirectorio(dir);
		
		System.out.println("5. " + leerSalida(Lanzador.para(HIJO, "mixto")));
		
		System.out.println("6. Salida y error por separado:");
		leerSalidaYError(Lanzador.para(HIJO, "mixto"), dir.resolve("error.txt"));
		
		System.out.println("7. " + leerConCodificacion(
				ordenShell("echo hola"), Charset.defaultCharset()));
		
		System.out.println("8. " + enviarYLeer(List.of("hola", "adios")));
		
		System.out.println("9. Conversacion:");
		conversar(List.of("uno", "dos"));
		Path entrada = dir.resolve("entrada.txt");
		Path salida = dir.resolve("salida.txt");
		Files.write(entrada, List.of("x", "y"));
		
		System.out.println("10. Codigo " + conFicheros(entrada, salida) + ", "
				+ Files.readAllLines(salida) + ", errores.log: "
				+ Files.readAllLines(dir.resolve("errores.log")));
		
		System.out.println("11. Descartado, codigo "
				+ descartarSalida(Lanzador.para(HIJO, "mixto")));
		
		System.out.println("12. Tuberia: " + tuberia(entrada));
		
		System.out.println("13. Shell: " + leerSalida(ordenShell("echo hola")));
		
		System.out.println("14. Codigo de salida: " + codigoDeSalida(3));
		
		System.out.println("15. Plazos:");
		esperarConPlazo(500, 3);
		esperarConPlazo(10_000, 1);
		
		System.out.println("16. Trabajar mientras el hijo corre:");
		trabajarMientras(500);
		publicar(dir.resolve("datos.txt"), List.of("listo"));
		
		System.out.println("17. Publicado: "
				+ Files.readAllLines(dir.resolve("datos.txt")));
	}
}