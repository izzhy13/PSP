package ejercicios.cuaderno1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class MiniTop {

    // Clase auxiliar interna para agrupar los datos ya extraídos de cada proceso
    private static class FilaProceso {
        final long pid;
        final String usuario;
        final String orden;
        final Duration tiempoCpu;
        final boolean infoCompleta;

        FilaProceso(long pid, String usuario, String orden, Duration tiempoCpu, boolean infoCompleta) {
            this.pid = pid;
            this.usuario = usuario;
            this.orden = orden;
            this.tiempoCpu = tiempoCpu;
            this.infoCompleta = infoCompleta;
        }
    }

    public static void main(String[] args) {
        List<FilaProceso> procesos = new ArrayList<>();
        Map<String, Integer> conteoPorUsuario = new TreeMap<>();
        int procesosSinInfoCompleta = 0;

        // Recorre todos los procesos visibles en el sistema desde ProcessHandle
        for (ProcessHandle ph : ProcessHandle.allProcesses().toList()) {
            long pid = ph.pid();
            ProcessHandle.Info info = ph.info();

            Optional<String> userOpt = info.user();
            Optional<String> cmdOpt = info.command();
            Optional<Duration> cpuOpt = info.totalCpuDuration();

            // Si falta alguno de los datos principales, lo marcamos como información no disponible/incompleta
            boolean completa = userOpt.isPresent() && cmdOpt.isPresent() && cpuOpt.isPresent();
            if (!completa) {
                procesosSinInfoCompleta++;
            }

            String usuario = userOpt.orElse("<no disponible>");
            String orden = cmdOpt.orElseGet(() -> info.commandLine().orElse("<no disponible>"));
            // Si no hay duración de CPU disponible, usamos Duration.ZERO para poder ordenar sin fallos
            Duration tiempoCpu = cpuOpt.orElse(Duration.ZERO);

            procesos.add(new FilaProceso(pid, usuario, orden, tiempoCpu, completa));

            // Contabiliza cuántos procesos corresponden a cada usuario (incluyendo "<no disponible>")
            conteoPorUsuario.put(usuario, conteoPorUsuario.getOrDefault(usuario, 0) + 1);
        }

        // Ordena de mayor a menor consumo de CPU usando un Comparator como clase anónima
        procesos.sort(new Comparator<FilaProceso>() {
            @Override
            public int compare(FilaProceso p1, FilaProceso p2) {
                // Invertimos el orden (p2 frente a p1) para que quede de mayor a menor
                int cmp = p2.tiempoCpu.compareTo(p1.tiempoCpu);
                if (cmp != 0) {
                    return cmp;
                }
                // Desempate determinista por PID ascendente
                return Long.compare(p1.pid, p2.pid);
            }
        });

        System.out.println("=== TOP 10 PROCESOS POR TIEMPO DE CPU ===");
        System.out.printf("%-8s %-22s %-16s %s%n", "PID", "USUARIO", "TIEMPO CPU", "ORDEN");
        System.out.println("-".repeat(80));

        int limite = Math.min(10, procesos.size());
        for (int i = 0; i < limite; i++) {
            FilaProceso p = procesos.get(i);
            System.out.printf(
                    "%-8d %-22s %-16s %s%n",
                    p.pid,
                    recortar(p.usuario, 22),
                    formatearDuracion(p.tiempoCpu),
                    p.orden
            );
        }

        System.out.println("\n=== PROCESOS POR USUARIO ===");
        for (Map.Entry<String, Integer> entry : conteoPorUsuario.entrySet()) {
            System.out.printf("  %s -> %d%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nProcesos con información no disponible / incompleta: " + procesosSinInfoCompleta);
    }

    private static String formatearDuracion(Duration d) {
        if (d == null || d.isZero()) {
            return "0.000s";
        }
        double segundos = d.toNanos() / 1_000_000_000.0;
        return String.format("%.3fs", segundos);
    }

    private static String recortar(String texto, int max) {
        if (texto.length() <= max) {
            return texto;
        }
        return texto.substring(0, max - 3) + "...";
    }
}