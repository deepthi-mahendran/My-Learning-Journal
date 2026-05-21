import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a Book in the library system.
 * Encapsulates title, author, and quantity for organized data management.
 * Source: Adapted from principles in Eck (2022) regarding object-oriented data grouping.
 */
class Book {
    String title;
    String author;
    int quantity;

    // Constructor to initialize a new book object
    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Override toString for clean display if needed, though not directly used in main output per spec.
    @Override
    public String toString() {
        return "Title: '" + title + "', Author: '" + author + "', Quantity: " + quantity;
    }
}

/**
 * Main Library System Application.
 * Demonstrates looping control structures and exception handling as outlined in the assignment rubric.
 */
public class LibrarySystem {

    // HashMap provides O(1) average time complexity for search, add, and update operations.
    // Using Title as Key ensures uniqueness and efficient retrieval.
    private static final Map<String, Book> library = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // The while loop ensures the program runs continuously until the user selects Exit.
        // This demonstrates the core concept of repetitive task execution (Eck, 2022, Section 3.3).
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            // Utilizing a switch statement for clean flow control.
            switch (choice) {
                case 1:
                    addBooks();
                    break;
                case 2:
                    borrowBooks();
                    break;
                case 3:
                    returnBooks();
                    break;
                case 4:
                    System.out.println("\nExiting Library System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break; // Unreachable but good practice
                default:
                    // This default case handles invalid integer inputs outside 1-4 range.
                    System.out.println("Error: Invalid menu selection. Please enter a number between 1 and 4.");
            }
        }
    }

    /**
     * Displays the user interface menu.
     */
    private static void displayMenu() {
        System.out.println("\n--- Library Management System ---");
        System.out.println("1. Add Books");
        System.out.println("2. Borrow Books");
        System.out.println("3. Return Books");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Safely retrieves integer input from the user.
     * Incorporates Exception Handling (try-catch) to manage InputMismatchException.
     * This enhances maintainability by preventing program crashes from non-numeric input (Coding with John, 2020).
     *
     * @return The validated integer choice.
     */
    private static int getUserChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            // Error handling: Clear the invalid token from scanner buffer.
            System.out.println("Error: Input must be a numeric value.");
        } finally {
            scanner.nextLine(); // Consume newline character to prevent infinite loop.
        }
        return choice;
    }

    /**
     * Option 1: Adds a new book or updates quantity of an existing book.
     * Uses nested conditional logic to check for existing keys in the HashMap.
     */
    private static void addBooks() {
        System.out.println("\n--- Add Books ---");
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Enter Quantity: ");
        int quantity = 0;
        try {
            quantity = scanner.nextInt();
            if (quantity <= 0) {
                System.out.println("Error: Quantity must be a positive integer.");
                scanner.nextLine();
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid quantity. Please enter a valid number.");
            scanner.nextLine(); // Clear buffer
            return;
        }
        scanner.nextLine(); // Consume newline

        // Check if book exists: Control structure for decision making.
        if (library.containsKey(title)) {
            // Book exists: Update quantity.
            Book existingBook = library.get(title);
            existingBook.quantity += quantity;
            System.out.printf("Success: Updated '%s'. New total quantity: %d\n",
                    title, existingBook.quantity);
        } else {
            // New book: Add to collection.
            Book newBook = new Book(title, author, quantity);
            library.put(title, newBook);
            System.out.printf("Success: Added '%s' by %s with quantity %d.\n",
                    title, author, quantity);
        }
    }

    /**
     * Option 2: Borrows books, reducing available quantity.
     * Demonstrates validation of business rules using if-else structures.
     */
    private static void borrowBooks() {
        System.out.println("\n--- Borrow Books ---");
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine().trim();

        // Check existence
        if (!library.containsKey(title)) {
            System.out.printf("Error: The book '%s' does not exist in the library system.\n", title);
            return;
        }

        System.out.print("Enter number of books to borrow: ");
        int borrowQty = 0;
        try {
            borrowQty = scanner.nextInt();
            if (borrowQty <= 0) {
                System.out.println("Error: Borrow quantity must be positive.");
                scanner.nextLine();
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid quantity input.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine(); // Consume newline

        Book book = library.get(title);
        
        // Nested control structure: Check availability.
        if (book.quantity >= borrowQty) {
            book.quantity -= borrowQty;
            System.out.printf("Success: Borrowed %d copies of '%s'. Remaining copies: %d\n",
                    borrowQty, title, book.quantity);
        } else {
            // Error message for insufficient stock.
            System.out.printf("Error: Insufficient stock. Requested %d, but only %d available.\n",
                    borrowQty, book.quantity);
        }
    }

    /**
     * Option 3: Returns books, increasing available quantity.
     * Validates if the book exists before processing.
     */
    private static void returnBooks() {
        System.out.println("\n--- Return Books ---");
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine().trim();

        if (!library.containsKey(title)) {
            System.out.printf("Error: The book '%s' does not belong to this library system.\n", title);
            return; // Exit method early if book not found.
        }

        System.out.print("Enter number of books to return: ");
        int returnQty = 0;
        try {
            returnQty = scanner.nextInt();
            if (returnQty <= 0) {
                System.out.println("Error: Return quantity must be positive.");
                scanner.nextLine();
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid quantity input.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine(); // Consume newline

        Book book = library.get(title);
        book.quantity += returnQty;
        System.out.printf("Success: Returned %d copies of '%s'. New total quantity: %d\n",
                returnQty, title, book.quantity);
    }
}