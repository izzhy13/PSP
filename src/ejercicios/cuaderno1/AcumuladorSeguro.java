package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AcumuladorSeguro {
	public static void main(String[] args) throws IOException {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		PrintWriter salida = new PrintWriter(System.out, true, StandardCharsets.UTF_8);

		long saldo = 100;
		String linea;
		while ((linea = entrada.readLine()) != null) {
			String orden = linea.strip();
			if (orden.isEmpty()) {
				continue;
			}

			long cambio = Long.parseLong(orden); // acepta "+25" o "-40"
			if (saldo + cambio < 0) {
				salida.println("ERROR:SIN_SALDO");
				System.exit(4);
			}

			saldo += cambio;
			salida.println("SALDO:" + saldo);
		}
		
		try {
            Thread.sleep(5000); // duerme 5 segundos antes de salir
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.exit(0);
	}
}
