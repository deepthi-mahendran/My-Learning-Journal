/**
 * Student class representing individual student records.
 * Uses private instance variables for encapsulation.
 */
public class Student {
    private String name;
    private String studentId;
    private int age;
    private String grade;

    // Constructor
    public Student(String name, String studentId, int age, String grade) {
        this.name = name;
        this.studentId = studentId;
        this.age = age;
        this.grade = grade;
    }

    // Getters
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public int getAge() { return age; }
    public String getGrade() { return grade; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "ID: " + studentId + ", Name: " + name + 
               ", Age: " + age + ", Grade: " + grade;
    }
}