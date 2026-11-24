package ejercicios.cuaderno1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class Sumador {

	public static void main(String[] args) throws NumberFormatException, IOException {
		Path destino = Path.of("suma.txt");
        long suma = 0;
        
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
        	
            String linea;
            while ((linea = entrada.readLine()) != null) {
                String limpia = linea.strip();
                if (!limpia.isEmpty()) {
                    suma += Long.parseLong(limpia);
                }
            }
        }

        Files.writeString(destino, String.valueOf(suma), StandardCharsets.UTF_8);
    }
        
}
