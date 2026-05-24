import java.util.*;

/**
 * Central management class with static methods and collections.
 * Maintains the list of all courses and overall grades per student.
 * 
 * @author Student Developer
 * @version 1.0
 */
public class CourseManagement {
    // Private static variables to store system-wide data
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static Map<Student, Double> overallCourseGrades = new HashMap<>();
    
    /**
     * Adds a new course to the system.
     * @param courseCode Unique code
     * @param name Course name
     * @param maxCapacity Maximum seats
     */
    public static void addCourse(String courseCode, String name, int maxCapacity) {
        // Validate capacity
        if (maxCapacity <= 0) {
            System.out.println("Error: Maximum capacity must be positive.");
            return;
        }
        // Check for duplicate course code
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(courseCode)) {
                System.out.println("Error: Course code " + courseCode + " already exists.");
                return;
            }
        }
        Course newCourse = new Course(courseCode, name, maxCapacity);
        courses.add(newCourse);
        System.out.println("Course " + courseCode + " added successfully.");
    }
    
    /**
     * Adds a new student to the system.
     * @param name Student name
     * @param id Student ID
     */
    public static void addStudent(String name, String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                System.out.println("Error: Student ID " + id + " already exists.");
                return;
            }
        }
        Student newStudent = new Student(name, id);
        students.add(newStudent);
        System.out.println("Student " + name + " (ID: " + id + ") added successfully.");
    }
    
    /**
     * Enrolls a student in a course.
     * @param studentId Student ID
     * @param courseCode Course code
     */
    public static void enrollStudent(String studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found.");
            return;
        }
        if (course == null) {
            System.out.println("Error: Course code " + courseCode + " not found.");
            return;
        }
        student.enrollStudent(course);
    }
    
    /**
     * Assigns a grade to a student for a specific course.
     * @param studentId Student ID
     * @param courseCode Course code
     * @param grade Numerical grade
     */
    public static void assignGrade(String studentId, String courseCode, double grade) {
        if (grade < 0.0 || grade > 100.0) {
            System.out.println("Error: Grade must be between 0.0 and 100.0.");
            return;
        }
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found.");
            return;
        }
        if (course == null) {
            System.out.println("Error: Course code " + courseCode + " not found.");
            return;
        }
        student.assignGrade(course, grade);
        // After grade assignment, the overall grade may be recalculated if desired,
        // but we will compute explicitly when called.
    }
    
    /**
     * Calculates and stores the overall course grade for a student.
     * @param studentId Student ID
     * @return The calculated overall grade
     */
    public static double calculateOverallGrade(String studentId) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found.");
            return -1.0;
        }
        double overall = student.calculateOverallGrade();
        overallCourseGrades.put(student, overall);
        System.out.println("Overall grade for " + student.getName() + " (ID: " + studentId + ") is: " + overall);
        return overall;
    }
    
    /**
     * Displays all courses in the system.
     */
    public static void listCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("\n--- Course List ---");
        for (Course c : courses) {
            System.out.println(c);
        }
        System.out.println("Total enrolled students across all courses: " + Course.getTotalEnrolledStudents());
    }
    
    /**
     * Displays all students in the system.
     */
    public static void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println(s.getName() + " (ID: " + s.getId() + ")");
        }
    }
    
    // Helper methods to find objects
    private static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }
    
    private static Course findCourseByCode(String code) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
    
    // Accessors for testing (not exposed in menu)
    public static List<Course> getCourses() { return courses; }
    public static List<Student> getStudents() { return students; }
}