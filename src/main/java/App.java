import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Modificar estado de tarea");
            System.out.println("3. Consultar estado de tarea");
            System.out.println("4. Eliminar estado de tarea");
            System.out.println("5. Diario de tareas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("👋 Saliendo del programa...");
                    break;
                case 2:
                    // ModificarTarea.modificarEstado();
                    break;
                case 3:
                    System.out.println("👋 Saliendo del programa...");
                    break;    
                case 4:
                    System.out.println("👋 Saliendo del programa...");
                    break;
                case 5:
                    System.out.println("👋 Saliendo del programa...");
                    break;
                case 6:
                    System.out.println("👋 Saliendo del programa...");
                    break;
                default:
                    System.out.println("❌ Opción no válida");
            }
        } while (opcion != 6);

        scanner.close();
    }
}
