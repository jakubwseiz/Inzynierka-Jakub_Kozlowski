import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUDApp {

    private List<String> items;
    private Scanner scanner;

    public CRUDApp() {
        this.items = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== CRUD App ===");
            System.out.println("1. Dodaj element");
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
        items.add(newItem);
        System.out.println("Element dodany.");
    }

    private void displayElements() {
        if (items.isEmpty()) {
            System.out.println("Brak elementów.");
        } else {
            System.out.println("\nWszystkie elementy:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    private void updateElement() {
        if (items.isEmpty()) {
            System.out.println("Brak elementów do zaktualizowania.");
            return;
        }

        displayElements();
        int index = getIntInput("Wprowadź indeks elementu do zaktualizowania: ") - 1;

        if (index < 0 || index >= items.size()) {
            System.out.println("Niepoprawny indeks elementu.");
            return;
        }

        String updatedItem = getStringInput("Wprowadź zaktualizowany element: ");
        items.set(index, updatedItem);
        System.out.println("Element zaktualizowany.");
    }

    private void deleteElement() {
        if (items.isEmpty()) {
            System.out.println("Brak elementów do usunięcia.");
            return;
        }

        displayElements();
        int index = getIntInput("Wprowadź indeks elementu do usunięcia: ") - 1;

        if (index < 0 || index >= items.size()) {
            System.out.println("Niepoprawny indeks elementu.");
            return;
        }

        items.remove(index);
        System.out.println("Element usunięty.");
    }

    public static void main(String[] args) {
        CRUDApp app = new CRUDApp();
        app.run();
    }
}
