# Student Management System (GUI)

A Java Swing desktop application for educational administrators to manage student records, course enrollments, and grade assignments. The system provides an intuitive tabbed interface with full CRUD operations, input validation, and real-time data display.

## Overview

The **Student Management System** is a GUI-based Java application that allows administrators to:

- Add, update, and delete student records
- Enroll students in predefined courses
- Assign and view grades (on a 0.0–4.0 scale)
- View all students, courses, enrollments, and grades in one place

All data is stored in‑memory using Java collections (`HashMap`, `ArrayList`), making it ideal for small‑scale demonstrations or as a foundation for database integration.

The application demonstrates key Java concepts:
- **Swing GUI** (JFrame, JTabbedPane, JTable, JList, DefaultTableModel, etc.)
- **Event‑driven programming** (ActionListeners, lambdas)
- **Model‑View separation** (Student, Course, Enrollment model classes)
- **Collections & Streams** (filtering, searching enrollments)
- **Input validation** with user feedback dialogs

## Features

### Student Management
- **Add Student** – Auto‑generated ID (starting from 1000)
- **Update Student** – Select from dropdown, modify fields
- **Delete Student** – Removes student and all associated enrollments
- **Display** – Table view of all students (ID, Name, Email, Major, Phone)
- **Form validation** – Non‑empty fields, valid email format

### Course Enrollment
- **Predefined courses** – CS101, CS201, CS301, MATH101, ENG101
- **Enroll student** – Select a student and a course
- **Prevent duplicate enrollments** – Cannot enroll same student twice in a course
- **Live enrollment list** – Shows all current enrollments (Student → Course)

### Grade Management
- **Assign grades** – Choose a student, select an enrolled course, enter grade (0.0–4.0)
- **Dynamic course list** – Only shows courses the student is actually enrolled in
- **Grades summary** – Displays all students with their enrolled courses and assigned grades

### View All Records
- **Comprehensive report** – Button‑refreshable text area showing all students, courses, and enrollments with grades

## Project Structure

The entire application is contained in a single Java file (`StudentManagementGUI.java`) for simplicity. It includes:

```
StudentManagementGUI.java
├── StudentManagementGUI (main JFrame class)
│   ├── Data storage (Map<Integer,Student>, Map<Integer,Course>, List<Enrollment>)
│   ├── GUI panel creation methods
│   ├── Action handlers (add, update, delete, enroll, assign grade)
│   └── Helper methods (refresh displays, dialogs)
├── Student (model class)
├── Course (model class)
└── Enrollment (model class)
```

## Requirements

- **Java** – JDK 8 or later (Swing is included in standard Java)
- **No external libraries** – Pure Java SE

## Compilation and Execution

### Compile

Open a terminal/command prompt in the directory containing `StudentManagementGUI.java` and run:

```bash
javac StudentManagementGUI.java
```

### Run

```bash
java StudentManagementGUI
```

> **Note:** The application uses `SwingUtilities.invokeLater()` to ensure GUI creation happens on the Event Dispatch Thread (EDT), which is best practice for Swing applications.

## Usage Guide

When the application starts, you’ll see a window with four tabs:

### Student Management Tab

**Add a new student:**
1. Fill in Name, Email, Major, Phone (Phone is optional)
2. Click **Add Student**
3. A confirmation dialog shows the assigned student ID

**Update an existing student:**
1. Select a student ID from the dropdown
2. Fields automatically populate with current data
3. Modify any fields (Name and Email are required)
4. Click **Update Student**

**Delete a student:**
1. Select a student ID from the dropdown
2. Click **Delete Student** and confirm
3. All enrollments for that student are also removed

**Clear form** – Resets all input fields and dropdown selection.

The student table updates automatically after any add/update/delete.

### Course Enrollment Tab

**Enroll a student in a course:**
1. Select a course from the dropdown (e.g., `CS101 - Introduction to Programming`)
2. Select a student from the student dropdown (format: `ID - Name`)
3. Click **Enroll Student**

The **Current Enrollments** list shows all successful enrollments. Duplicate enrollments are prevented.

### Grade Management Tab

**Assign a grade to a student for a specific course:**
1. Select a student from the dropdown
2. The **course dropdown** automatically shows only courses that student is enrolled in
3. Select a course
4. Enter a grade (numeric, 0.0 – 4.0, e.g., `3.5`)
5. Click **Assign Grade**

The **Grades Summary** text area displays all students with their enrolled courses and assigned grades (formatted to two decimal places). This area updates automatically after each grade assignment.

### View All Records Tab

Click **Refresh Display** to generate a complete report showing:
- All students with their details
- All courses with codes and credits
- All enrollments with student names, course titles, and grades (if assigned)

## Data Models

### Student
| Field  | Type   | Description                    |
|--------|--------|--------------------------------|
| id     | int    | Auto‑generated (1000, 1001…)  |
| name   | String | Full name                      |
| email  | String | Must contain '@' and '.'       |
| major  | String | Academic major                 |
| phone  | String | Optional contact number        |

### Course (predefined)
| id | courseCode | title                         | credits |
|----|------------|-------------------------------|---------|
| 1  | CS101      | Introduction to Programming   | 3       |
| 2  | CS201      | Data Structures               | 4       |
| 3  | CS301      | Database Management           | 3       |
| 4  | MATH101    | Calculus I                    | 4       |
| 5  | ENG101     | English Composition           | 3       |

### Enrollment
| Field     | Type   | Description                         |
|-----------|--------|-------------------------------------|
| studentId | int    | References a Student                |
| courseId  | int    | References a Course                 |
| grade     | double | 0.0 = not assigned, otherwise 0.0–4.0 |

## Input Validation

The system validates user input at every step:

| Field / Action            | Validation Rule                                                                 |
|---------------------------|---------------------------------------------------------------------------------|
| Student name / email / major | Cannot be empty (phone is optional)                                         |
| Email format              | Must contain '@' and at least one '.' after the '@'                            |
| Grade                     | Must be a number between 0.0 and 4.0 inclusive                                 |
| Duplicate enrollment      | Prevents enrolling the same student in the same course twice                   |
| Course selection (grade tab) | Only shows courses the selected student is actually enrolled in             |

All errors are reported via `JOptionPane` error dialogs with clear messages.

## Example Workflow

1. **Add a student**  
   - Name: `John Doe`  
   - Email: `john.doe@example.com`  
   - Major: `Computer Science`  
   - Phone: `555-1234`  
   → ID `1000` assigned.

2. **Add another student**  
   - Name: `Jane Smith`  
   - Email: `jane.smith@example.com`  
   - Major: `Mathematics`  
   → ID `1001` assigned.

3. **Enroll John in CS101**  
   - Select course `CS101`, student `1000 - John Doe` → **Enroll**.

4. **Assign a grade**  
   - Switch to **Grade Management** tab.  
   - Select `John Doe`, then course `Introduction to Programming`.  
   - Enter grade `3.7` → **Assign Grade**.

5. **View all records**  
   - Go to **View All Records** tab, click **Refresh Display**.  
   - Report shows John’s enrollment and grade, and Jane with no enrollments yet.

## Extending the System

The modular design makes it easy to extend:

- **Persist data** – Replace `HashMap` and `ArrayList` with a database (JDBC) or file I/O.
- **Add new courses** – Extend `initializeCourses()` or add a course management tab.
- **Drop enrollments** – Add a delete button in the enrollment list.
- **Calculate GPA** – Compute average grade per student and display.
- **Search/filter** – Add search fields to filter students by name or major.
- **Export reports** – Add buttons to save the full display to a text file.

## Screenshots
<img width="1099" height="866" alt="Screenshot 2026-05-23 200411" src="https://github.com/user-attachments/assets/bf359e75-5a3d-4d06-a59f-b73c91cc0245" />

<img width="1080" height="854" alt="Screenshot 2026-05-23 200656" src="https://github.com/user-attachments/assets/f99660f2-80a5-4ea0-8344-c56cfb1ad4dc" />

<img width="1092" height="855" alt="Screenshot 2026-05-23 200735" src="https://github.com/user-attachments/assets/35354b76-4a1b-4a5d-80b0-45400a9553b4" />

<img width="1111" height="867" alt="Screenshot 2026-05-23 200831" src="https://github.com/user-attachments/assets/b1d98fd3-3406-4f81-8cff-ae31cf0a4b59" />

<img width="1102" height="863" alt="Screenshot 2026-05-23 200848" src="https://github.com/user-attachments/assets/adb713c0-8ce1-43c9-8af6-47d802bb9519" />

## License

This project is created for educational purposes to demonstrate Java Swing, object‑oriented programming, and event‑driven GUI design. You are free to use, modify, and distribute it for learning.

---
