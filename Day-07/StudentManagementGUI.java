import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Student Management System GUI Application
 * 
 * This application provides an intuitive interface for educational 
 * administrators to manage student records, course enrollments, 
 * and grade assignments using Java Swing.
 * 
 * @author Student - Developer
 * @version 1.0
 * @see "Eck, D. J. (2022). Introduction to Programming using Java version 9"
 */
public class StudentManagementGUI extends JFrame {
    
    // Data storage using in-memory collections
    private Map<Integer, Student> students;
    private Map<Integer, Course> courses;
    private List<Enrollment> enrollments;
    private int nextStudentId;
    
    // GUI Components - Student Management Panel
    private JPanel studentPanel;
    private JTextField txtName, txtEmail, txtMajor, txtPhone;
    private JComboBox<Integer> cmbStudentId;
    private DefaultTableModel studentTableModel;
    private JTable studentTable;
    
    // GUI Components - Course Enrollment Panel
    private JComboBox<String> cmbCourse;
    private JComboBox<String> cmbStudentForEnroll;
    private JList<String> enrollmentList;
    private DefaultListModel<String> enrollmentListModel;
    
    // GUI Components - Grade Management Panel
    private JComboBox<String> cmbStudentForGrade;
    private JComboBox<String> cmbCourseForGrade;
    private JTextField txtGrade;
    private JTextArea gradeDisplayArea;
    
    /**
     * Constructor - Initializes the GUI and data models
     */
    public StudentManagementGUI() {
        // Initialize data structures
        students = new HashMap<>();
        courses = new HashMap<>();
        enrollments = new ArrayList<>();
        nextStudentId = 1000;
        
        // Set up sample course data
        initializeCourses();
        
        // Configure the main window
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Create tabbed pane for organized navigation
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Add panels to tabs
        tabbedPane.addTab("Student Management", createStudentPanel());
        tabbedPane.addTab("Course Enrollment", createCourseEnrollmentPanel());
        tabbedPane.addTab("Grade Management", createGradeManagementPanel());
        tabbedPane.addTab("View All Records", createDisplayPanel());
        
        add(tabbedPane);
        
        // Initial population of dropdowns
        refreshStudentDropdowns();
        
        setVisible(true);
    }
    
    /**
     * Initialize predefined courses
     */
    private void initializeCourses() {
        courses.put(1, new Course(1, "CS101", "Introduction to Programming", 3));
        courses.put(2, new Course(2, "CS201", "Data Structures", 4));
        courses.put(3, new Course(3, "CS301", "Database Management", 3));
        courses.put(4, new Course(4, "MATH101", "Calculus I", 4));
        courses.put(5, new Course(5, "ENG101", "English Composition", 3));
    }
    
    // ==================== STUDENT MANAGEMENT PANEL ====================
    
    /**
     * Creates the Student Management panel with form fields and a table
     */
    private JPanel createStudentPanel() {
        studentPanel = new JPanel(new BorderLayout(10, 10));
        studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form Panel for input
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Student ID selection
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Student ID:"), gbc);
        gbc.gridx = 1;
        cmbStudentId = new JComboBox<>();
        cmbStudentId.addActionListener(e -> loadStudentDataForUpdate());
        formPanel.add(cmbStudentId, gbc);
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(15);
        formPanel.add(txtName, gbc);
        
        // Email field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(15);
        formPanel.add(txtEmail, gbc);
        
        // Major field
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Major:"), gbc);
        gbc.gridx = 1;
        txtMajor = new JTextField(15);
        formPanel.add(txtMajor, gbc);
        
        // Phone field
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(15);
        formPanel.add(txtPhone, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add Student");
        JButton btnUpdate = new JButton("Update Student");
        JButton btnDelete = new JButton("Delete Student");
        JButton btnClear = new JButton("Clear Form");
        
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearStudentForm());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        // Table to display students
        String[] columns = {"ID", "Name", "Email", "Major", "Phone"};
        studentTableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(studentTableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        // Assemble the panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        studentPanel.add(northPanel, BorderLayout.NORTH);
        studentPanel.add(scrollPane, BorderLayout.CENTER);
        
        return studentPanel;
    }
    
    /**
     * Adds a new student to the system
     */
    private void addStudent() {
        try {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String major = txtMajor.getText().trim();
            String phone = txtPhone.getText().trim();
            
            // Input validation
            if (name.isEmpty() || email.isEmpty() || major.isEmpty()) {
                showErrorDialog("Please fill all required fields (*).");
                return;
            }
            
            // Validate email format
            if (!email.contains("@") || !email.contains(".")) {
                showErrorDialog("Please enter a valid email address.");
                return;
            }
            
            int studentId = nextStudentId++;
            Student student = new Student(studentId, name, email, major, phone);
            students.put(studentId, student);
            
            // Update the table display
            refreshStudentTable();
            refreshStudentDropdowns();
            clearStudentForm();
            
            showInfoDialog("Student added successfully!\nID: " + studentId);
            
        } catch (Exception ex) {
            showErrorDialog("Error adding student: " + ex.getMessage());
        }
    }
    
    /**
     * Updates an existing student's information
     */
    private void updateStudent() {
        try {
            if (cmbStudentId.getSelectedItem() == null) {
                showErrorDialog("Please select a student to update.");
                return;
            }
            
            int studentId = (Integer) cmbStudentId.getSelectedItem();
            Student student = students.get(studentId);
            
            if (student == null) {
                showErrorDialog("Student not found.");
                return;
            }
            
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String major = txtMajor.getText().trim();
            String phone = txtPhone.getText().trim();
            
            if (name.isEmpty() || email.isEmpty()) {
                showErrorDialog("Name and email are required fields.");
                return;
            }
            
            student.setName(name);
            student.setEmail(email);
            student.setMajor(major);
            student.setPhone(phone);
            
            refreshStudentTable();
            refreshStudentDropdowns();
            clearStudentForm();
            
            showInfoDialog("Student information updated successfully!");
            
        } catch (Exception ex) {
            showErrorDialog("Error updating student: " + ex.getMessage());
        }
    }
    
    /**
     * Deletes a student from the system
     */
    private void deleteStudent() {
        try {
            if (cmbStudentId.getSelectedItem() == null) {
                showErrorDialog("Please select a student to delete.");
                return;
            }
            
            int studentId = (Integer) cmbStudentId.getSelectedItem();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this student?\nThis will also remove all enrollments.",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Remove enrollments associated with this student
                enrollments.removeIf(e -> e.getStudentId() == studentId);
                students.remove(studentId);
                
                refreshStudentTable();
                refreshStudentDropdowns();
                refreshEnrollmentDisplay();
                clearStudentForm();
                
                showInfoDialog("Student deleted successfully!");
            }
        } catch (Exception ex) {
            showErrorDialog("Error deleting student: " + ex.getMessage());
        }
    }
    
    /**
     * Loads student data into the form for updating
     */
    private void loadStudentDataForUpdate() {
        if (cmbStudentId.getSelectedItem() != null) {
            int studentId = (Integer) cmbStudentId.getSelectedItem();
            Student student = students.get(studentId);
            if (student != null) {
                txtName.setText(student.getName());
                txtEmail.setText(student.getEmail());
                txtMajor.setText(student.getMajor());
                txtPhone.setText(student.getPhone());
            }
        }
    }
    
    /**
     * Clears the student input form
     */
    private void clearStudentForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtMajor.setText("");
        txtPhone.setText("");
        cmbStudentId.setSelectedIndex(-1);
    }
    
    /**
     * Refreshes the student table display
     */
    private void refreshStudentTable() {
        studentTableModel.setRowCount(0);
        for (Student s : students.values()) {
            Object[] row = {s.getId(), s.getName(), s.getEmail(), s.getMajor(), s.getPhone()};
            studentTableModel.addRow(row);
        }
    }
    
    // ==================== COURSE ENROLLMENT PANEL ====================
    
    /**
     * Creates the Course Enrollment panel
     */
    private JPanel createCourseEnrollmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel for enrollment actions
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Select Course:"), gbc);
        gbc.gridx = 1;
        cmbCourse = new JComboBox<>();
        for (Course c : courses.values()) {
            cmbCourse.addItem(c.getCourseCode() + " - " + c.getTitle());
        }
        topPanel.add(cmbCourse, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Select Student:"), gbc);
        gbc.gridx = 1;
        cmbStudentForEnroll = new JComboBox<>();
        topPanel.add(cmbStudentForEnroll, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        JButton btnEnroll = new JButton("Enroll Student");
        btnEnroll.addActionListener(e -> enrollStudent());
        topPanel.add(btnEnroll, gbc);
        
        // List to display current enrollments
        enrollmentListModel = new DefaultListModel<>();
        enrollmentList = new JList<>(enrollmentListModel);
        enrollmentList.setBorder(BorderFactory.createTitledBorder("Current Enrollments"));
        JScrollPane scrollPane = new JScrollPane(enrollmentList);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Enrolls a student in a selected course
     */
    private void enrollStudent() {
        try {
            if (cmbStudentForEnroll.getSelectedItem() == null) {
                showErrorDialog("Please select a student to enroll.");
                return;
            }
            
            if (cmbCourse.getSelectedItem() == null) {
                showErrorDialog("Please select a course.");
                return;
            }
            
            String studentInfo = (String) cmbStudentForEnroll.getSelectedItem();
            int studentId = Integer.parseInt(studentInfo.split("-")[0].trim());
            String courseInfo = (String) cmbCourse.getSelectedItem();
            String courseCode = courseInfo.split(" - ")[0];
            
            Course selectedCourse = null;
            for (Course c : courses.values()) {
                if (c.getCourseCode().equals(courseCode)) {
                    selectedCourse = c;
                    break;
                }
            }
            
            if (selectedCourse == null) {
                showErrorDialog("Course not found.");
                return;
            }
            
            // FIX: Use final variables for lambda capture
            final int finalStudentId = studentId;
            final int finalCourseId = selectedCourse.getId();
            
            // Check if already enrolled
            boolean alreadyEnrolled = enrollments.stream()
                .anyMatch(e -> e.getStudentId() == finalStudentId && e.getCourseId() == finalCourseId);
            
            if (alreadyEnrolled) {
                showErrorDialog("Student is already enrolled in this course.");
                return;
            }
            
            Enrollment enrollment = new Enrollment(studentId, selectedCourse.getId());
            enrollments.add(enrollment);
            
            refreshEnrollmentDisplay();
            refreshGradeDropdowns();
            
            showInfoDialog("Student successfully enrolled in " + selectedCourse.getTitle());
            
        } catch (Exception ex) {
            showErrorDialog("Error enrolling student: " + ex.getMessage());
        }
    }
    
    /**
     * Refreshes the enrollment list display
     */
    private void refreshEnrollmentDisplay() {
        enrollmentListModel.clear();
        for (Enrollment e : enrollments) {
            Student student = students.get(e.getStudentId());
            Course course = courses.get(e.getCourseId());
            if (student != null && course != null) {
                enrollmentListModel.addElement(student.getName() + " → " + course.getTitle());
            }
        }
    }
    
    // ==================== GRADE MANAGEMENT PANEL ====================
    
    /**
     * Creates the Grade Management panel
     */
    private JPanel createGradeManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel for grade assignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Student:"), gbc);
        gbc.gridx = 1;
        cmbStudentForGrade = new JComboBox<>();
        cmbStudentForGrade.addActionListener(e -> loadCoursesForStudent());
        formPanel.add(cmbStudentForGrade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Select Course:"), gbc);
        gbc.gridx = 1;
        cmbCourseForGrade = new JComboBox<>();
        formPanel.add(cmbCourseForGrade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Grade (0.0 - 4.0):"), gbc);
        gbc.gridx = 1;
        txtGrade = new JTextField(10);
        formPanel.add(txtGrade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JButton btnAssignGrade = new JButton("Assign Grade");
        btnAssignGrade.addActionListener(e -> assignGrade());
        formPanel.add(btnAssignGrade, gbc);
        
        // Text area to display grades
        gradeDisplayArea = new JTextArea(15, 40);
        gradeDisplayArea.setEditable(false);
        gradeDisplayArea.setBorder(BorderFactory.createTitledBorder("Grades Summary"));
        JScrollPane scrollPane = new JScrollPane(gradeDisplayArea);
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Loads courses for the selected student
     */
    private void loadCoursesForStudent() {
        cmbCourseForGrade.removeAllItems();
        if (cmbStudentForGrade.getSelectedItem() != null) {
            String studentInfo = (String) cmbStudentForGrade.getSelectedItem();
            int studentId = Integer.parseInt(studentInfo.split("-")[0].trim());
            
            for (Enrollment e : enrollments) {
                if (e.getStudentId() == studentId) {
                    Course course = courses.get(e.getCourseId());
                    if (course != null) {
                        cmbCourseForGrade.addItem(course.getTitle());
                    }
                }
            }
        }
    }
    
    /**
     * Assigns a grade to a student for a specific course
     */
    private void assignGrade() {
        try {
            if (cmbStudentForGrade.getSelectedItem() == null) {
                showErrorDialog("Please select a student.");
                return;
            }
            
            if (cmbCourseForGrade.getSelectedItem() == null) {
                showErrorDialog("Please select a course.");
                return;
            }
            
            String gradeText = txtGrade.getText().trim();
            if (gradeText.isEmpty()) {
                showErrorDialog("Please enter a grade.");
                return;
            }
            
            double grade = Double.parseDouble(gradeText);
            if (grade < 0.0 || grade > 4.0) {
                showErrorDialog("Grade must be between 0.0 and 4.0.");
                return;
            }
            
            String studentInfo = (String) cmbStudentForGrade.getSelectedItem();
            int studentId = Integer.parseInt(studentInfo.split("-")[0].trim());
            String courseTitle = (String) cmbCourseForGrade.getSelectedItem();
            
            Course course = null;
            for (Course c : courses.values()) {
                if (c.getTitle().equals(courseTitle)) {
                    course = c;
                    break;
                }
            }
            
            // Update the grade
            for (Enrollment e : enrollments) {
                if (e.getStudentId() == studentId && e.getCourseId() == course.getId()) {
                    e.setGrade(grade);
                    break;
                }
            }
            
            refreshGradeDisplay();
            txtGrade.setText("");
            showInfoDialog("Grade assigned successfully!");
            
        } catch (NumberFormatException ex) {
            showErrorDialog("Invalid grade format. Please enter a numeric value.");
        } catch (Exception ex) {
            showErrorDialog("Error assigning grade: " + ex.getMessage());
        }
    }
    
    /**
     * Refreshes the grade display area
     */
    private void refreshGradeDisplay() {
        gradeDisplayArea.setText("");
        StringBuilder sb = new StringBuilder();
        sb.append("=== STUDENT GRADES ===\n\n");
        
        for (Student s : students.values()) {
            sb.append("Student: ").append(s.getName()).append(" (ID: ").append(s.getId()).append(")\n");
            boolean hasGrades = false;
            
            for (Enrollment e : enrollments) {
                if (e.getStudentId() == s.getId()) {
                    Course course = courses.get(e.getCourseId());
                    if (course != null) {
                        sb.append("  • ").append(course.getTitle());
                        if (e.getGrade() > 0) {
                            sb.append(" → Grade: ").append(String.format("%.2f", e.getGrade()));
                        } else {
                            sb.append(" → Grade: Not assigned");
                        }
                        sb.append("\n");
                        hasGrades = true;
                    }
                }
            }
            
            if (!hasGrades) {
                sb.append("  (Not enrolled in any courses)\n");
            }
            sb.append("\n");
        }
        
        gradeDisplayArea.setText(sb.toString());
    }
    
    // ==================== DISPLAY PANEL ====================
    
    /**
     * Creates the comprehensive display panel
     */
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea fullDisplay = new JTextArea();
        fullDisplay.setEditable(false);
        fullDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JButton btnRefresh = new JButton("Refresh Display");
        // FIX: changed lambda parameter from 'e' to 'evt' to avoid conflict with loop variable
        btnRefresh.addActionListener(evt -> {
            StringBuilder sb = new StringBuilder();
            sb.append("==================== STUDENT MANAGEMENT SYSTEM ====================\n\n");
            sb.append("STUDENTS:\n");
            sb.append("-------------------------------------------------------------------\n");
            for (Student s : students.values()) {
                sb.append(s.toString()).append("\n");
            }
            sb.append("\nCOURSES:\n");
            sb.append("-------------------------------------------------------------------\n");
            for (Course c : courses.values()) {
                sb.append(c.toString()).append("\n");
            }
            sb.append("\nENROLLMENTS:\n");
            sb.append("-------------------------------------------------------------------\n");
            for (Enrollment e : enrollments) {
                Student s = students.get(e.getStudentId());
                Course c = courses.get(e.getCourseId());
                if (s != null && c != null) {
                    sb.append(s.getName()).append(" → ").append(c.getTitle());
                    if (e.getGrade() > 0) {
                        sb.append(" [Grade: ").append(String.format("%.2f", e.getGrade())).append("]");
                    }
                    sb.append("\n");
                }
            }
            fullDisplay.setText(sb.toString());
        });
        
        panel.add(btnRefresh, BorderLayout.NORTH);
        panel.add(new JScrollPane(fullDisplay), BorderLayout.CENTER);
        
        return panel;
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Refreshes all student dropdown menus
     */
    private void refreshStudentDropdowns() {
        cmbStudentId.removeAllItems();
        cmbStudentForEnroll.removeAllItems();
        cmbStudentForGrade.removeAllItems();
        
        for (Student s : students.values()) {
            cmbStudentId.addItem(s.getId());
            cmbStudentForEnroll.addItem(s.getId() + " - " + s.getName());
            cmbStudentForGrade.addItem(s.getId() + " - " + s.getName());
        }
    }
    
    /**
     * Refreshes grade-related dropdowns and displays
     */
    private void refreshGradeDropdowns() {
        refreshStudentDropdowns();
        refreshGradeDisplay();
    }
    
    /**
     * Displays an error dialog box
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays an information dialog box
     */
    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Main method - application entry point
     */
    public static void main(String[] args) {
        // Ensure GUI creation happens on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new StudentManagementGUI());
    }
}

/**
 * Model class representing a Student
 */
class Student {
    private int id;
    private String name;
    private String email;
    private String major;
    private String phone;
    
    public Student(int id, String name, String email, String major, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.major = major;
        this.phone = phone;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %-20s | Email: %-25s | Major: %-20s | Phone: %s",
                id, name, email, major, phone);
    }
}

/**
 * Model class representing a Course
 */
class Course {
    private int id;
    private String courseCode;
    private String title;
    private int credits;
    
    public Course(int id, String courseCode, String title, int credits) {
        this.id = id;
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
    }
    
    public int getId() { return id; }
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    
    @Override
    public String toString() {
        return String.format("%s: %s (%d credits)", courseCode, title, credits);
    }
}

/**
 * Model class representing an Enrollment with grade
 */
class Enrollment {
    private int studentId;
    private int courseId;
    private double grade;
    
    public Enrollment(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = 0.0;
    }
    
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }
}