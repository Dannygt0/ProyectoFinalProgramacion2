import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;

public class ConexionTest {
    public static void main(String[] args) {
        try {
            // Cargar credenciales
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\daniel.munoz\\Desktop\\ProyectoFinalProgramacion2\\src\\proyectoprogra2-d9a75-firebase-adminsdk-fbsvc-b7f4a5c2f1.json"
);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Inicializar Firebase solo una vez
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            // Obtener instancia de Firestore
            Firestore db = FirestoreClient.getFirestore();
            System.out.println("Conexión establecida correctamente con Firestore.\n");

            // Crear instancia de tu clase Consultar
            Consultar consultar = new Consultar(db);

            // 🧾 Mostrar todas las tareas desde Firestore
            consultar.mostrarTareas();

        } catch (IOException e) {
            System.out.println(" Error al conectar con Firestore: " + e.getMessage());
        }
    }
}
