import java.util.*;

/**
 * Represents a student with name, ID, enrolled courses, and grades.
 * Provides instance methods to enroll in courses and assign grades.
 * 
 * @author Student Developer
 * @version 1.0
 */
public class Student {
    // Private instance variables for encapsulation
    private String name;
    private String id;
    private List<Course> enrolledCourses;
    private Map<Course, Double> grades; // Maps a course to its assigned grade
    
    /**
     * Constructor initializes a student with name and ID.
     * @param name Student's full name
     * @param id Unique student identifier
     */
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
    }
    
    // Getter and setter methods for student information
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    /**
     * Enrolls the student in a course.
     * @param course The course to enroll in
     * @return true if enrollment succeeded, false if course is full or already enrolled
     */
    public boolean enrollStudent(Course course) {
        if (enrolledCourses.contains(course)) {
            System.out.println("Student " + name + " is already enrolled in " + course.getCourseCode());
            return false;
        }
        // Attempt to add student to course (checks capacity)
        if (course.addStudent(this)) {
            enrolledCourses.add(course);
            System.out.println("Successfully enrolled " + name + " in " + course.getCourseCode());
            return true;
        } else {
            System.out.println("Enrollment failed: " + course.getCourseCode() + " is at maximum capacity.");
            return false;
        }
    }
    
    /**
     * Assigns a grade to the student for a specific course.
     * @param course The course for which grade is assigned
     * @param grade Numerical grade (0.0 - 100.0)
     */
    public void assignGrade(Course course, double grade) {
        if (!enrolledCourses.contains(course)) {
            System.out.println("Error: Student " + name + " is not enrolled in " + course.getCourseCode());
            return;
        }
        grades.put(course, grade);
        System.out.println("Assigned grade " + grade + " to " + name + " for " + course.getCourseCode());
    }
    
    /**
     * Retrieves the grade for a given course.
     * @param course The course to query
     * @return The grade, or null if not assigned
     */
    public Double getGrade(Course course) {
        return grades.get(course);
    }
    
    /**
     * Calculates the overall average grade for the student across all graded courses.
     * @return Average grade, or 0.0 if no grades assigned
     */
    public double calculateOverallGrade() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0.0;
        for (double g : grades.values()) {
            sum += g;
        }
        return sum / grades.size();
    }
    
    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', id='" + id + "'}";
    }
}