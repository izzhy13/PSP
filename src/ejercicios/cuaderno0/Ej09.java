package ejercicios.cuaderno0;

import java.io.IOException;

public class Ej09 {

	public static void main(String[] args) throws Exception {
		System.out.println("--- Sin intérprete de órdenes");
		ProcessBuilder a = new ProcessBuilder("ls", "*.txt");
		int ca = a.inheritIO().start().waitFor();
		System.out.println("Código: " + ca);
		
		System.out.println("--- Con sh -c"); //sh -c en linux cmd /c en windows
		ProcessBuilder b = new ProcessBuilder("sh", "-c", "ls *.txt");
		int cb = b.inheritIO().start().waitFor();
		System.out.println("Código: " + cb);
	}

}

//no abusar del interprete Si solo necesitas listar ficheros, hazlo en Java, sin procesos:
// Files.newDirectoryStream(Path.of("."), "*.txt")