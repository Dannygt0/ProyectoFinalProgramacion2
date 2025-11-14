import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AgregarTarea{

    private static Firestore conectarFirestore() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/proyectoprogra2-d9a75-firebase-adminsdk-fbsvc-b7f4a5c2f1[1].json");

        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return options.getService();
    }

    public static void agregarTarea() {
        try {
            Firestore db = conectarFirestore();
            Scanner in = new Scanner(System.in);

            System.out.println("=== AGREGAR TAREA ===");

            // El usuario ingresa el número de la tarea
            System.out.print("Ingrese número de tarea: ");
            int numeroTarea = in.nextInt();
            in.nextLine(); // limpiar buffer

            System.out.print("Ingrese descripción de la tarea: ");
            String descripcion = in.nextLine();

            // Datos de la tarea
            Map<String, Object> tarea = new HashMap<>();
            tarea.put("descripcion", descripcion);
            tarea.put("estaRealizada", false);

            // Guardar la tarea usando el ID proporcionado por el usuario
            DocumentReference docRef = db.collection("tareas")
                                        .document(String.valueOf(numeroTarea));

            ApiFuture<?> future = docRef.set(tarea);
            future.get();  // esperar a que termine

            System.out.println("Tarea agregada exitosamente con ID: " + numeroTarea);

        } catch (Exception e) {
            System.out.println("Error al agregar la tarea: " + e.getMessage());
        }
    }
}
