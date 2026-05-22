import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class providing the administrator console interface.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("  Student Record Management System");
        System.out.println("=====================================");

        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addNewStudent();
                        break;
                    case 2:
                        updateStudentInfo();
                        break;
                    case 3:
                        viewStudentDetails();
                        break;
                    case 4:
                        StudentManagement.viewAllStudents();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please select 1-5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Administrator Menu ---");
        System.out.println("1. Add New Student");
        System.out.println("2. Update Student Information");
        System.out.println("3. View Student by ID");
        System.out.println("4. View All Students");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addNewStudent() {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();

        if (StudentManagement.addStudent(name, id, age, grade)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Error: Failed to add student. Check for duplicate ID or invalid data.");
        }
    }

    private static void updateStudentInfo() {
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter new Name (press Enter to keep current): ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Age (-1 to keep current): ");
        int newAge = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Grade (press Enter to keep current): ");
        String newGrade = scanner.nextLine();

        if (StudentManagement.updateStudent(id, newName.isEmpty() ? null : newName, 
                                           newAge < 0 ? -1 : newAge, 
                                           newGrade.isEmpty() ? null : newGrade)) {
            System.out.println("Student information updated successfully!");
        } else {
            System.out.println("Error: Student ID not found.");
        }
    }

    private static void viewStudentDetails() {
        System.out.print("Enter Student ID to view: ");
        String id = scanner.nextLine();
        StudentManagement.viewStudent(id);
    }
}