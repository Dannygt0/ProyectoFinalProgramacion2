import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.FileInputStream;

public class ConexionFirestore {

    private static Firestore db;

    // Método estático para obtener la instancia
    public static Firestore conectarFirestore() throws Exception {
        if (db == null) { // Solo se crea una vez
            FileInputStream serviceAccount =
                    new FileInputStream("src/proyectoprogra2-d9a75-firebase-adminsdk-fbsvc-b7f4a5c2f1.json");

            FirestoreOptions options = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            db = options.getService();
        }

        return db;
    }
}
