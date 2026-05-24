# University Course Enrollment & Grade Management System

A console‑based Java application that simulates a university course enrollment and grade management system.  
Administrators can add courses and students, enroll students in courses, assign grades, calculate overall averages, and list all data.

---

## 📋 Features

- **Course Management** – add courses with a unique code, name, and maximum capacity.
- **Student Management** – add students with a unique ID and name.
- **Enrollment** – enroll a student in a course (respects course capacity).
- **Grade Assignment** – assign a numeric grade (0–100) to a student for a specific course.
- **Overall Grade Calculation** – compute the average grade across all courses the student has been graded in.
- **Listing** – display all courses (with current enrollment) and all students.
- **Static Tracking** – total number of student‑course registrations is tracked across all courses.

---

## 🛠️ Technologies

- **Java** – JDK 8 or higher
- No external libraries – pure standard Java

---

## 📁 Project Structure

```
├── Course.java           // Course entity: code, name, capacity, enrolled students
├── Student.java          // Student entity: name, ID, enrolled courses, grades map
├── CourseManagement.java // Core logic: static collections and operations
└── UniversitySystem.java // Console menu (entry point)
```

---

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) installed  
- Command line / terminal access

### Compilation

Compile all `.java` files together:

```bash
javac *.java
```

### Execution

Run the main class:

```bash
java UniversitySystem
```

---

## 🖥️ Usage

When you run the program, you will see a menu:

```
===== Administrator Menu =====
1. Add a new course
2. Add a new student
3. Enroll a student in a course
4. Assign a grade to a student
5. Calculate overall course grade for a student
6. List all courses
7. List all students
8. Exit
```

Follow the prompts to enter data. Examples:

- **Add a course** – enter `CS101`, `Intro to Programming`, `30`
- **Add a student** – enter `Alice Johnson`, `S12345`
- **Enroll** – student ID `S12345`, course code `CS101`
- **Assign grade** – student ID `S12345`, course code `CS101`, grade `85.5`
- **Calculate overall grade** – shows the average of all graded courses for that student

---

## 📚 Class Overview

### `Course`
| Method | Description |
|--------|-------------|
| `addStudent(Student s)` | Enrolls a student if capacity not reached; increments static counter |
| `getTotalEnrolledStudents()` | Static method – returns total registrations across all courses |

### `Student`
| Method | Description |
|--------|-------------|
| `enrollStudent(Course c)` | Adds course to student’s list (calls `addStudent` on the course) |
| `assignGrade(Course c, double grade)` | Stores grade for the course (must be enrolled first) |
| `calculateOverallGrade()` | Returns average of all assigned grades |

### `CourseManagement`
| Method | Description |
|--------|-------------|
| `addCourse(...)`, `addStudent(...)` | Adds to internal `ArrayList`s with duplicate checks |
| `enrollStudent(...)`, `assignGrade(...)` | Uses helper methods to find objects, delegates to `Student` |
| `calculateOverallGrade(...)` | Calls student’s method and stores result in a `HashMap` |
| `listCourses()`, `listStudents()` | Prints all entries plus total enrollment count |

### `UniversitySystem`
- Provides a text‑based menu loop.
- Handles input validation (integers, doubles, ranges).
- Calls the appropriate `CourseManagement` static methods.

---

## ⚠️ Important Notes

- **Grade range** – grades must be between `0.0` and `100.0`.  
- **Capacity** – once a course reaches its maximum capacity, no more students can be enrolled.  
- **Duplicate prevention** – course codes and student IDs must be unique.  
- **Overall grade** – calculated only from **graded** courses (ungraded courses are ignored). If no grades exist, the average is `0.0`.  
- **Data persistence** – all data is stored in memory only. Data is lost when the program exits.

---

## 🧪 Example Workflow

```
1. Add course: CS101, Programming, 2
2. Add course: MATH200, Calculus, 2
3. Add student: John Doe, J001
4. Add student: Jane Smith, J002
5. Enroll J001 in CS101 → success
6. Enroll J002 in CS101 → success
7. Enroll J001 in MATH200 → success
8. Assign grade 90 to J001 for CS101
9. Assign grade 85 to J001 for MATH200
10. Calculate overall grade for J001 → average = 87.5
11. List courses → CS101 (2/2), MATH200 (1/2)
12. Total enrolled students (static) → 3
```

---

## 🤝 Contributing

This project was created as a learning exercise. If you have suggestions or find bugs, feel free to open an issue or submit a pull request.

---

## 📄 License

This project is provided for educational purposes. You may use, modify, and distribute it freely.

---
