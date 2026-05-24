import java.util.*;

/**
 * Represents a course with code, name, maximum capacity, and enrolled students.
 * Tracks total enrollment across all courses using a static variable.
 * 
 * @author Student Developer
 * @version 1.0
 */
public class Course {
    // Private instance variables
    private String courseCode;
    private String name;
    private int maxCapacity;
    private int currentEnrollment;
    private List<Student> enrolledStudents; // For internal tracking
    
    // Static variable to track total enrolled students across all courses
    private static int totalEnrolledStudents = 0;
    
    /**
     * Constructor initializes the course.
     * @param courseCode Unique course identifier (e.g., "CS101")
     * @param name Course title
     * @param maxCapacity Maximum number of students allowed
     */
    public Course(String courseCode, String name, int maxCapacity) {
        this.courseCode = courseCode;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
        this.enrolledStudents = new ArrayList<>();
    }
    
    // Public getter methods (no setters to maintain immutability of course info)
    public String getCourseCode() { return courseCode; }
    public String getName() { return name; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }
    
    /**
     * Adds a student to the course if capacity permits.
     * @param student Student to enroll
     * @return true if enrollment successful, false if capacity reached
     */
    public boolean addStudent(Student student) {
        if (currentEnrollment < maxCapacity) {
            enrolledStudents.add(student);
            currentEnrollment++;
            totalEnrolledStudents++; // Increment static counter
            return true;
        }
        return false;
    }
    
    /**
     * Static method to retrieve total number of enrolled students across all course instances.
     * @return Total enrollment count (student-course registrations)
     */
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }
    
    /**
     * Resets total enrollment (useful for testing, not exposed in menu).
     */
    public static void resetTotalEnrollment() {
        totalEnrolledStudents = 0;
    }
    
    @Override
    public String toString() {
        return courseCode + " - " + name + " (Enrolled: " + currentEnrollment + "/" + maxCapacity + ")";
    }
}