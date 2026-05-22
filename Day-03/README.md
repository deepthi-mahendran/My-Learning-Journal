# Student Management System

A complete Java console application for managing student records.  
It demonstrates **encapsulation**, **static data management**, and a **menu‑driven user interface**.

## Project Structure

```
.
├── Student.java           # Student entity (encapsulated data)
├── StudentManagement.java # Business logic (static operations)
└── Main.java              # Console menu & user interaction
```

## Features

- ✅ Add a new student (duplicate ID check, input validation)
- ✅ Update student details (name, age, grade) by ID
- ✅ View a single student by ID
- ✅ View all students
- ✅ Graceful error handling (invalid input, missing IDs)
- ✅ Console‑based administrator menu (no external libraries)

## How to Compile & Run

1. **Compile** all three Java files together:

   ```bash
   javac Student.java StudentManagement.java Main.java
   ```

2. **Run** the application (entry point is `Main`):

   ```bash
   java Main
   ```

## Usage

Once started, the interactive menu appears:

```
=====================================
  Student Record Management System
=====================================

--- Administrator Menu ---
1. Add New Student
2. Update Student Information
3. View Student by ID
4. View All Students
5. Exit
Enter your choice:
```

### Example Session

```
Enter your choice: 1
Enter Student Name: Alice Johnson
Enter Student ID: S1001
Enter Age: 20
Enter Grade: A
Student added successfully!

Enter your choice: 4
=== All Students (1) ===
ID: S1001, Name: Alice Johnson, Age: 20, Grade: A

Enter your choice: 3
Enter Student ID to view: S1001
Student Details:
ID: S1001, Name: Alice Johnson, Age: 20, Grade: A

Enter your choice: 5
Exiting the system. Goodbye!
```

## Class Details (Quick Reference)

### `Student`

| Field       | Type     | Description               |
|-------------|----------|---------------------------|
| `name`      | `String` | Student’s full name       |
| `studentId` | `String` | Unique identifier         |
| `age`       | `int`    | Age                       |
| `grade`     | `String` | Academic grade (e.g., "A") |

### `StudentManagement` (static methods)

| Method                                                | Description                                    |
|-------------------------------------------------------|------------------------------------------------|
| `addStudent(name, id, age, grade)`                   | Adds a student → `true` if success            |
| `updateStudent(id, newName, newAge, newGrade)`       | Updates fields (pass `null`/`-1` to skip)     |
| `getStudentById(id)`                                 | Returns `Student` or `null`                   |
| `viewAllStudents()`                                  | Prints all students                           |
| `viewStudent(id)`                                    | Prints a single student                       |
| `getTotalStudents()`                                 | Returns current student count                 |

### `Main` (console interface)

- Displays the menu loop
- Handles `InputMismatchException` and other errors
- Reads user input and calls the appropriate `StudentManagement` methods

## Possible Enhancements

- Remove student by ID
- Save/load records to a file (CSV, JSON, or binary)
- Search students by name or grade
- Age range validation (0–120)
- Sort students by name or ID

## License

Free to use for educational and personal purposes.
