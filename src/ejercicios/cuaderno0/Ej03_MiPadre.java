package ejercicios.cuaderno0;

import java.util.Optional;

public class Ej03_MiPadre {

	public static void main(String[] args) {
		
		ProcessHandle yo = ProcessHandle.current();
		System.out.println("Mi PID es " + yo);
		
		Optional<ProcessHandle> padre = yo.parent();
		
		if (padre.isPresent()) {
			ProcessHandle p = padre.get();
			System.out.println("PID padre " + p.pid());
			System.out.println("Orden del padre: " + p.info().command().orElse("Desconocida"));

		} else {
			System.out.println("No puedo ver a mi padre");
		}

	}

}
