import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import com.google.cloud.Timestamp;

public class Consultar {

    private Firestore db;       // Ya no es final
    private final Scanner sc = new Scanner(System.in);

    public Consultar() {
        try {
            this.db = ConexionFirestore.conectarFirestore(); 
        } catch (Exception e) {
            System.out.println("Error al conectar a Firestore: " + e.getMessage());
        }
    }

    public void mostrarDatos() {
        try {
            mostrarTareas();
            mostrarDiario();
            regresarMenu();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error al consultar datos: " + e.getMessage());
        }
    }

    private void mostrarTareas() throws InterruptedException, ExecutionException {
    ApiFuture<QuerySnapshot> future = db.collection("tareas").get();
    List<QueryDocumentSnapshot> documentos = future.get().getDocuments();

    System.out.println("\n----- TAREAS -----");
    if (documentos.isEmpty()) {
        System.out.println("No tiene ninguna tarea registrada.");
    } else {
        System.out.println("Listado general de tareas:");
        for (QueryDocumentSnapshot doc : documentos) {
            String descripcion = doc.getString("descripcion");
            Boolean realizada = doc.getBoolean("realizada");

            System.out.println("- Descripción: " + descripcion);
            System.out.println("  Realizada: " + (realizada != null && realizada ? "Sí" : "No"));
        }

        System.out.println("\nTareas no realizadas:");
        mostrarTareasPorEstado(false);

        System.out.println("\nTareas realizadas:");
        mostrarTareasPorEstado(true);
    }
}

private void mostrarTareasPorEstado(boolean estado) throws InterruptedException, ExecutionException {
    ApiFuture<QuerySnapshot> future = db.collection("tareas")
            .whereEqualTo("realizada", estado)
            .get();

    List<QueryDocumentSnapshot> tareas = future.get().getDocuments();

    if (tareas.isEmpty()) {
        System.out.println("(No hay tareas " + (estado ? "realizadas" : "pendientes") + ")");
    } else {
        for (QueryDocumentSnapshot doc : tareas) {
            String descripcion = doc.getString("descripcion");
            System.out.println("- " + descripcion + " → " + (estado ? "Realizada" : "Pendiente"));
        }
    }
}


    private void mostrarDiario() throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> future = db.collection("diario").get();
        List<QueryDocumentSnapshot> documentos = future.get().getDocuments();

        System.out.println("\n----- DIARIO -----");
        if (documentos.isEmpty()) {
            System.out.println("No hay entradas en el diario.");
        } else {
            for (QueryDocumentSnapshot doc : documentos) {
                String comentario = doc.getString("comentario");
                Timestamp ts = doc.getTimestamp("fechaHora");
                String fechaHora = (ts != null) ? ts.toDate().toString() : "No especificada";

                System.out.println("- ID: " + doc.getId());
                System.out.println("  Comentario: " + (comentario != null ? comentario : "Sin comentario"));
                System.out.println("  Fecha y hora: " + fechaHora);
                System.out.println();
            }
        }
    }

    private void regresarMenu() {
        System.out.println("\nPresione ENTER para regresar al menú principal...");
        sc.nextLine();
    }
}
