import java.util.Scanner;

import com.google.cloud.firestore.Firestore;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Modificar estado de tarea");
            System.out.println("3. Consulta General");
            System.out.println("4. Eliminar estado de tarea");
            System.out.println("5. Agregar comentario en diario");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    AgregarTarea.agregarTarea();
                    break;
                case 2:
                    ModificarTarea.modificarEstado();
                    break;
                case 3:
                    Consultar consultar = new Consultar();
                    consultar.mostrarDatos();
                    System.out.println("👋 Saliendo del programa...");
                    break;
                case 4:
                    EliminarTarea.eliminarTarea();
                    break;
                case 5:
                    try {
                        Firestore db = ConexionFirestore.conectarFirestore();
                        AgregarDiario.agregarEntrada(db);
                    } catch (Exception e) {
                        System.out.println("Error al conectar con Firestore: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 6);

        scanner.close();
    }
}
