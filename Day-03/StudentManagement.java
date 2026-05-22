import java.util.ArrayList;

/**
 * StudentManagement class handles all student record operations using 
 * private static variables and static methods.
 */
public class StudentManagement {
    private static ArrayList<Student> students = new ArrayList<>();
    private static int totalStudents = 0;

    /**
     * Adds a new student to the record.
     * @param name Student's name
     * @param studentId Unique student ID
     * @param age Student's age
     * @param grade Student's grade
     * @return true if added successfully
     */
    public static boolean addStudent(String name, String studentId, int age, String grade) {
        if (name == null || name.trim().isEmpty() || studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        // Check for duplicate ID
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return false;
            }
        }
        students.add(new Student(name.trim(), studentId.trim(), age, grade.trim()));
        totalStudents++;
        return true;
    }

    /**
     * Updates student information by ID.
     * @param studentId ID of student to update
     * @param newName New name (null to keep existing)
     * @param newAge New age (-1 to keep existing)
     * @param newGrade New grade (null to keep existing)
     * @return true if update successful, false if ID not found
     */
    public static boolean updateStudent(String studentId, String newName, int newAge, String newGrade) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                if (newName != null && !newName.trim().isEmpty()) {
                    s.setName(newName.trim());
                }
                if (newAge > 0) {
                    s.setAge(newAge);
                }
                if (newGrade != null && !newGrade.trim().isEmpty()) {
                    s.setGrade(newGrade.trim());
                }
                return true;
            }
        }
        return false; // ID not found
    }

    /**
     * Returns a student by ID or null if not found.
     */
    public static Student getStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Displays all students.
     */
    public static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        System.out.println("\n=== All Students (" + totalStudents + ") ===");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    /**
     * Displays a specific student by ID.
     */
    public static void viewStudent(String studentId) {
        Student s = getStudentById(studentId);
        if (s != null) {
            System.out.println("\nStudent Details:");
            System.out.println(s);
        } else {
            System.out.println("Error: Student with ID " + studentId + " not found.");
        }
    }

    // Getter for total students (for potential future use)
    public static int getTotalStudents() {
        return totalStudents;
    }
}