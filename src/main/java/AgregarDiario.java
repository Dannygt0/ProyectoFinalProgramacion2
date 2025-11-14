import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AgregarDiario {

    public static void agregarEntrada(Firestore db) {

        Scanner in = new Scanner(System.in);

        System.out.println("=== AGREGAR COMENTARIO AL DIARIO ===");

        System.out.print("Ingrese el comentario: ");
        String comentario = in.nextLine();

        System.out.print("Ingrese categoria (opcional): ");
        String categoria = in.nextLine();

        // Datos a guardar
        Map<String, Object> entrada = new HashMap<>();
        entrada.put("comentario", comentario);
        entrada.put("categoria", categoria.isEmpty() ? "N/A" : categoria);
        entrada.put("fecha", Timestamp.now());

        try {
            ApiFuture<DocumentReference> future = db.collection("diario").add(entrada);
            DocumentReference docRef = future.get();

            System.out.println("Comentario agregado exitosamente con ID: " + docRef.getId());

        } catch (Exception e) {
            System.out.println("Error al guardar el comentario: " + e.getMessage());
        }
    }
}
