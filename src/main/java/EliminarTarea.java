import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.List;
import java.util.Scanner;

public class EliminarTarea {
    public static void eliminarTarea() {
        try {
            Firestore db = ConexionFirestore.conectarFirestore();
            Scanner sc = new Scanner(System.in);
           //Obtener tareas
            ApiFuture<QuerySnapshot> future = db.collection("tareas").get();
            List<QueryDocumentSnapshot> tareas = future.get().getDocuments();
            System.out.println("\n===== ELIMINAR TAREA =====");
            if (tareas.isEmpty()) {
                System.out.println("No hay tareas registradas.");
                return;
            }
            //Mostrar tareas con ID
            System.out.println("\n--- LISTADO DE TAREAS ---");
            for (QueryDocumentSnapshot doc : tareas) {

                String descripcion = doc.getString("descripcion");
                Boolean realizada = doc.getBoolean("realizada");

                System.out.println("- ID: " + doc.getId() +
                        " | Descripción: " + descripcion +
                        " | Estado: " + (realizada != null && realizada ? "Realizada" : "Pendiente"));
            }
            //Solicitar ID
            System.out.print("\nIngrese el ID EXACTO de la tarea que desea eliminar: ");
            String id = sc.nextLine().trim();
            //Verificar si existe el ID
            DocumentReference docRef = db.collection("tareas").document(id);
            DocumentSnapshot doc = docRef.get().get();
            if (!doc.exists()) {
                System.out.println("No existe una tarea con ese ID.");
                return;
            }
            //Confirmacion
            System.out.print("¿Esta seguro que desea eliminar esta tarea? (S/N): ");
            String respuesta = sc.nextLine().trim().toUpperCase();
            if (!respuesta.equals("S")) {
                System.out.println("Cancelado. No se elimino ninguna tarea.");
                return;
            }
            //Eliminar tarea
            ApiFuture<WriteResult> deleteFuture = docRef.delete();
            deleteFuture.get(); // esperar respuesta

            System.out.println("Tarea eliminada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al eliminar la tarea: " + e.getMessage());
        }
    }
}
