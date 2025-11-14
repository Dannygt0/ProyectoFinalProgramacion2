import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import java.util.*;

public class ModificarTarea {
    private static Firestore conectarFirestore() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/proyectoprogra2-d9a75-firebase-adminsdk-fbsvc-b7f4a5c2f1[1].json");

        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return options.getService();
    }
    public static void modificarEstado() {
        try {
            Firestore db = ConexionFirestore.conectarFirestore();
            Scanner scanner = new Scanner(System.in);

            ApiFuture<QuerySnapshot> future = db.collection("tareas")
                    .whereEqualTo("realizada", false)
                    .get();

            List<QueryDocumentSnapshot> tareas = future.get().getDocuments();

            if (tareas.isEmpty()) {
                System.out.println("No hay tareas pendientes por realizar.");
                return;
            }

            System.out.println("\n=== LISTADO DE TAREAS PENDIENTES ===");
            for (int i = 0; i < tareas.size(); i++) {
                System.out.println((i + 1) + ". " + tareas.get(i).getString("descripcion"));
            }

            System.out.print("\nIngrese el número de la tarea que desea marcar como realizada: ");
            int seleccion = scanner.nextInt();

            if (seleccion < 1 || seleccion > tareas.size()) {
                System.out.println("Número inválido.");
                return;
            }

            DocumentSnapshot tareaSeleccionada = tareas.get(seleccion - 1);

            ApiFuture<WriteResult> resultado = db.collection("tareas")
                    .document(tareaSeleccionada.getId())
                    .update("realizada", true);

            System.out.println("Tarea marcada como realizada.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
