import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Consultar {

    private final Firestore db;
    private final Scanner sc = new Scanner(System.in);

    public Consultar(Firestore db) {
        this.db = db;
    }

    public void mostrarTareas() {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("tareas").get();
            List<QueryDocumentSnapshot> documentos = future.get().getDocuments();

            if (documentos.isEmpty()) {
                System.out.println("\nNo tiene ninguna tarea registrada.");
                regresarMenu();
                return;
            }

            System.out.println("\nLISTADO GENERAL DE TAREAS:");
            for (QueryDocumentSnapshot doc : documentos) {
                System.out.println("- " + doc.getId() + ": " + doc.getData());
            }

            System.out.println("\nTAREAS NO REALIZADAS:");
            mostrarPorEstado(false);

            System.out.println("\nTAREAS REALIZADAS:");
            mostrarPorEstado(true);

            regresarMenu();

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error al consultar tareas: " + e.getMessage());
        }
    }

    private void mostrarPorEstado(boolean estado) throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> future = db.collection("tareas")
                .whereEqualTo("realizada", estado)
                .get();

        List<QueryDocumentSnapshot> tareas = future.get().getDocuments();

        if (tareas.isEmpty()) {
            System.out.println("(No hay tareas con estado " + (estado ? "realizadas" : "pendientes") + ")");
        } else {
            for (QueryDocumentSnapshot doc : tareas) {
                System.out.println("- " + doc.getString("nombre") + " → " + (estado ? "Realizada" : "Pendiente"));
            }
        }
    }

    private void regresarMenu() {
        System.out.println("\nPresione ENTER para regresar al menú principal...");
        sc.nextLine();
        // Aquí puedes llamar al método que muestra tu menú principal
        // Por ejemplo: MenuPrincipal.mostrar();
    }
}
