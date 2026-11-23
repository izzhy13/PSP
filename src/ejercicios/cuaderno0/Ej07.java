package ejercicios.cuaderno0;

public class Ej07 {
	public static void main(String[] args) throws Exception {
		ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Saludo");
		// sin inheritIO() Útil para ver qué pasa; el padre no puede procesarla.
		int codigo = pb.start().waitFor();
		System.out.println("Padre: el hijo terminó con código " + codigo);
	}
}
