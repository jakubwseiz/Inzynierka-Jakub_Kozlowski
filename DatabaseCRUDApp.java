import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseCRUDApp {

    private Connection connection;
    private Scanner scanner;

    public DatabaseCRUDApp() {
        this.connection = null;
        this.scanner = new Scanner(System.in);
    }

    public void connect(String dbName) {
        try {
            // Ustawienia bazy danych SQLite w pamięci
            String url = "jdbc:sqlite::memory:";
            // Utwórz połączenie
            connection = DriverManager.getConnection(url);

            // Utwórz tabelę w bazie danych, jeśli nie istnieje
            String createTableQuery = "CREATE TABLE IF NOT EXISTS items (id INTEGER PRIMARY KEY, name TEXT)";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
            }

            System.out.println("Połączono z bazą danych.");
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy łączeniu z bazą danych: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Rozłączono z bazą danych.");
            }
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy rozłączaniu z bazą danych: " + e.getMessage());
        }
    }

    public void run() {
        System.out.println("=== Database CRUD App ===");

        String dbName = getStringInput("Wprowadź nazwę bazy danych: ");
        connect(dbName);

        boolean running = true;
        while (running) {
            System.out.println("\n1. Dodaj element");
            System.out.println("2. Wyświetl wszystkie elementy");
            System.out.println("3. Zaktualizuj element");
            System.out.println("4. Usuń element");
            System.out.println("5. Wyjdź");

            int choice = getIntInput("Wybierz opcję: ");

            switch (choice) {
                case 1:
                    addElement();
                    break;
                case 2:
                    displayElements();
                    break;
                case 3:
                    updateElement();
                    break;
                case 4:
                    deleteElement();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
            }
        }

        disconnect();
        scanner.close();
        System.out.println("Zakończono program.");
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Niepoprawne dane. Wprowadź liczbę: ");
        }
        return scanner.nextInt();
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private void addElement() {
        String newItem = getStringInput("Wprowadź nowy element: ");
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO items (name) VALUES (?)")) {
            statement.setString(1, newItem);
            statement.executeUpdate();
            System.out.println("Element dodany.");
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy dodawaniu elementu: " + e.getMessage());
        }
    }

    private void displayElements() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM items")) {
            List<String> items = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                items.add(id + ". " + name);
            }
            if (items.isEmpty()) {
                System.out.println("Brak elementów.");
            } else {
                System.out.println("\nWszystkie elementy:");
                items.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy wyświetlaniu elementów: " + e.getMessage());
        }
    }

    private void updateElement() {
        displayElements();
        int id = getIntInput("Wprowadź ID elementu do zaktualizowania: ");

        String updatedItem = getStringInput("Wprowadź zaktualizowany element: ");
        try (PreparedStatement statement = connection.prepareStatement("UPDATE items SET name = ? WHERE id = ?")) {
            statement.setString(1, updatedItem);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Element zaktualizowany.");
            } else {
                System.out.println("Nie znaleziono elementu o podanym ID.");
            }
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy aktualizowaniu elementu: " + e.getMessage());
        }
    }

    private void deleteElement() {
        displayElements();
        int id = getIntInput("Wprowadź ID elementu do usunięcia: ");
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM items WHERE id = ?")) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Element usunięty.");
            } else {
                System.out.println("Nie znaleziono elementu o podanym ID.");
            }
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy usuwaniu elementu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseCRUDApp app = new DatabaseCRUDApp();
        app.run();
    }
}
