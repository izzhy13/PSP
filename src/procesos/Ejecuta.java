package procesos;

import java.io.IOException;

public class Ejecuta {

	public static void main(String[] args) throws IOException,InterruptedException {

		try {
		
		ProcessBuilder pb = new ProcessBuilder(args);

		pb.inheritIO();

		Process p = pb.start();
		int codigo = p.waitFor(); 

		System.out.println("PID " + p.pid() + ", código " + codigo);

		} catch (IOException e) {
			
		} catch (InterruptedException e) {
			
		}

	}

}
