import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            // Ruta al archivo de credenciales
            FileInputStream serviceAccount = new FileInputStream("src/proyectoprogra2-d9a75-firebase-adminsdk-fbsvc-b7f4a5c2f1.json");

            FirestoreOptions options = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            Firestore db = options.getService();
            System.out.println("✅ Conectado correctamente a Firestore");

            // Ejemplo: imprimir la referencia a una colección
            System.out.println("Colección de ejemplo: " + db.collection("usuarios").getPath());

        } catch (IOException e) {
            System.out.println("❌ Error al conectar con Firestore: " + e.getMessage());
        }
    }
}

