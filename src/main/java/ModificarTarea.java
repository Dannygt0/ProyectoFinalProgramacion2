import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ModificarTarea {

    private static Firestore conectarFirestore() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/proyectoprogra2-d9a75-firebase-adminsdk-fbsvc-b7f4a5c2f1.json");

        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return options.getService();
    }

    public static void modificarEstado() {
        try {
            Firestore db = conectarFirestore();
            Scanner scanner = new Scanner(System.in);

            ApiFuture<QuerySnapshot> future = db.collection("tareas")
                    .whereEqualTo("estaRealizada", false)
                    .get();

            List<QueryDocumentSnapshot> tareas = future.get().getDocuments();

            if (tareas.isEmpty()) {
                System.out.println("No hay tareas pendientes por realizar.");
                return;
            }

            // 🔹 Mostrar las tareas con índices
            System.out.println("\n=== LISTADO DE TAREAS PENDIENTES ===");
            for (int i = 0; i < tareas.size(); i++) {
                System.out.println((i + 1) + ". " + tareas.get(i).getString("descripcion"));
            }

            // 🔹 Pedir al usuario que elija una
            System.out.print("\nIngrese el número de la tarea que desea marcar como realizada: ");
            int seleccion = scanner.nextInt();

            if (seleccion < 1 || seleccion > tareas.size()) {
                System.out.println("Número inválido. Intente nuevamente.");
                return;
            }

            // 🔹 Obtener el documento seleccionado
            DocumentSnapshot tareaSeleccionada = tareas.get(seleccion - 1);
            String idDocumento = tareaSeleccionada.getId();

            // 🔹 Actualizar el campo estaRealizada a true
            ApiFuture<WriteResult> resultado = db.collection("tareas")
                    .document(idDocumento)
                    .update("estaRealizada", true);

            System.out.println("Tarea modificada exitosamente el: " + resultado.get().getUpdateTime());

        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Error al obtener o modificar las tareas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}
