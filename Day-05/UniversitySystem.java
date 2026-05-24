import java.util.Scanner;

/**
 * Command-line interface for the Course Enrollment and Grade Management System.
 * Provides menu-driven interaction for administrators.
 * 
 * @author Student Developer
 * @version 1.0
 */
public class UniversitySystem {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== University Course Enrollment & Grade Management System ===");
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1: addNewCourse(); break;
                case 2: addNewStudent(); break;
                case 3: enrollStudentInCourse(); break;
                case 4: assignGradeToStudent(); break;
                case 5: calculateStudentOverallGrade(); break;
                case 6: CourseManagement.listCourses(); break;
                case 7: CourseManagement.listStudents(); break;
                case 8: System.out.println("Exiting system. Goodbye!"); running = false; break;
                default: System.out.println("Invalid choice. Please enter 1-8.");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n===== Administrator Menu =====");
        System.out.println("1. Add a new course");
        System.out.println("2. Add a new student");
        System.out.println("3. Enroll a student in a course");
        System.out.println("4. Assign a grade to a student");
        System.out.println("5. Calculate overall course grade for a student");
        System.out.println("6. List all courses");
        System.out.println("7. List all students");
        System.out.println("8. Exit");
        System.out.print("Choice: ");
    }
    
    private static void addNewCourse() {
        System.out.print("Enter course code (e.g., CS101): ");
        String code = scanner.nextLine().trim();
        System.out.print("Enter course name: ");
        String name = scanner.nextLine().trim();
        int capacity = getIntInput("Enter maximum capacity: ");
        CourseManagement.addCourse(code, name, capacity);
    }
    
    private static void addNewStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        CourseManagement.addStudent(name, id);
    }
    
    private static void enrollStudentInCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().trim();
        CourseManagement.enrollStudent(studentId, courseCode);
    }
    
    private static void assignGradeToStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().trim();
        double grade = getDoubleInput("Enter grade (0.0 - 100.0): ");
        CourseManagement.assignGrade(studentId, courseCode, grade);
    }
    
    private static void calculateStudentOverallGrade() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();
        CourseManagement.calculateOverallGrade(studentId);
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0 || value > 100) {
                    System.out.println("Grade must be between 0 and 100. Try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }
}
