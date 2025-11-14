import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AgregarTarea {

    public static void agregarTarea() {
        try {
            Firestore db = ConexionFirestore.conectarFirestore();
            Scanner in = new Scanner(System.in);

            System.out.println("=== AGREGAR TAREA ===");
            System.out.print("Ingrese descripción de la tarea: ");
            String descripcion = in.nextLine();

            Map<String, Object> tarea = new HashMap<>();
            tarea.put("descripcion", descripcion);
            tarea.put("realizada", false); // ✔ nombre consistente

            ApiFuture<DocumentReference> future = db.collection("tareas").add(tarea);
            DocumentReference docRef = future.get();

            System.out.println("Tarea agregada con ID: " + docRef.getId());

        } catch (Exception e) {
            System.out.println("Error al agregar la tarea: " + e.getMessage());
        }
    }
}
