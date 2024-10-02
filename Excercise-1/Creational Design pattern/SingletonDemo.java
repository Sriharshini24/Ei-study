import java.util.Scanner;

// Singleton Class
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Database connection established.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
            System.out.println("Reusing the existing connection.");
        }
        return instance;
    }

    public void releaseConnection() {
        System.out.println("Releasing the connection.");
        instance = null;
    }
}

// Client
public class SingletonDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1 to get a database connection or 2 to release it:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            DatabaseConnection connection = DatabaseConnection.getInstance();
        } else if (choice == 2) {
            DatabaseConnection.getInstance().releaseConnection();
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
