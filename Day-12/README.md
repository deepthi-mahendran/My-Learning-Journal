# Employee Stream Demo – Java Functional Programming

A Java application that demonstrates the **Function interface** and **Stream API** (Java 8+) for processing employee data. The program loads a dataset of employees, performs transformations, filtering, aggregations, and grouping – all using functional programming techniques.

---

## Overview

**EmployeeStreamDemo** is a console-based program that:

- Creates a list of `Employee` objects with validation (name, age, department, salary).
- Applies **functional interfaces** (`Function`, `Predicate`) and **stream pipelines**.
- Performs common data operations: mapping, filtering, averaging, grouping, sorting, and parallel stream benchmarking.

The program serves as a practical reference for learning Java’s functional programming features introduced in Java 8.

---

## Features

| Feature                     | Description                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| **Dataset loading**         | Hard‑coded list of 10 employees (simulates reading from a file or database)|
| **Function interface**      | `Function<Employee, String>` mapping employee to `"name - department"`      |
| **Stream mapping**          | Transform each `Employee` into a formatted string                           |
| **New collection creation** | `Collectors.toList()` produces a separate list of mapped strings            |
| **Average salary**          | `mapToDouble().average()` over all employees                                |
| **Filter by age**           | `Predicate<Employee>` to find employees older than a threshold              |
| **Department grouping**     | `Collectors.groupingBy` with `averagingDouble` for average salary per dept  |
| **Parallel stream benchmark** | Compares sequential vs. parallel stream performance for average calculation |
| **Top 3 earners**           | Sort by salary descending and `limit(3)`                                    |

All operations use **lazy evaluation** and **short‑circuiting** where appropriate.

---

## Key Concepts Demonstrated

- **Functional interfaces** – `Function<T,R>`, `Predicate<T>`
- **Stream API** – `stream()`, `parallelStream()`, `map()`, `filter()`, `sorted()`, `limit()`, `collect()`
- **Method references** – `Employee::getSalary`
- **Lambda expressions** – `emp -> emp.getName() + " - " + emp.getDepartment()`
- **Collectors** – `toList()`, `groupingBy()`, `averagingDouble()`
- **Optional handling** – `average().orElse(0.0)`
- **Time measurement** – `System.nanoTime()` for benchmarking

---

## Requirements

- **Java** – JDK 8 or higher (Stream API is available from Java 8 onward)
- **No external libraries** – pure Java SE

---

## Compilation and Execution

### Compile

```bash
javac EmployeeStreamDemo.java
```

### Run

```bash
java EmployeeStreamDemo
```

No command‑line arguments are required.

---

## Sample Output

```
=== Employee Management System ===
Using Function interface and Stream API (Java 8+)

--- Initial Dataset ---
Employee{name='Alice Johnson', age=29, department='Engineering', salary=85000.00}
Employee{name='Bob Smith', age=35, department='Marketing', salary=72000.00}
...
Total employees: 10

--- Function Interface (map) ---
Alice Johnson - Engineering
Bob Smith - Marketing
Carol Davis - Engineering
David Brown - Sales
Eva Wilson - Marketing

--- New Collection (size: 10) ---
Alice Johnson - Engineering
Bob Smith - Marketing
...

--- Average Salary (All Employees) ---
$86,900.00

--- Employees above age 30 ---
Count: 7
Average Salary: $95,571.43

=== Bonus: Department Analysis ===
Engineering    | $116,250.00
Human Resources| $66,500.00
Marketing      | $75,500.00
Sales          | $60,000.00

=== Bonus: Parallel Stream Benchmark ===
Sequential: 4,523,200 ns
Parallel:   1,209,800 ns
Speedup:    3.74x

=== Bonus: Top 3 Earners ===
Ivy Clark            | Engineering    | $145,000.00
Frank Miller         | Engineering    | $125,000.00
Carol Davis          | Engineering    | $110,000.00
```

> *Actual times and values may vary depending on your machine.*

---

## Code Structure

| Component                  | Description                                                              |
|----------------------------|--------------------------------------------------------------------------|
| `Employee` class           | Immutable data class with validation in constructor (fail‑fast)          |
| `EmployeeStreamDemo` class | Contains `main()` and `loadEmployeeDataset()`                            |
| `nameDepartmentMapper`     | `Function<Employee, String>` constant – demonstrates functional interface |
| `loadEmployeeDataset()`    | Returns a hard‑coded `List<Employee>` for demonstration                  |
| Stream pipelines           | Each section shows a different stream operation                          |

The program is self‑contained – all code resides in one file for easy portability.

---

## Extending the Program

You can easily add more functionality:

- **Read from a CSV file** – replace `loadEmployeeDataset()` with file I/O.
- **Add more operations** – e.g., find employee with max salary, group by department then sort.
- **Write results to a file** – use `Files.write()` with stream output.
- **Add user input** – allow age threshold or department filter to be entered at runtime.
- **Use custom collectors** – e.g., `toMap()` for employee ID mapping.

Example – filter by department:

```java
employees.stream()
    .filter(emp -> emp.getDepartment().equals("Engineering"))
    .forEach(System.out::println);
```

---

## References

This demonstration is inspired by and references:

- Eck, D. J. (2022). *Introduction to Programming Using Java, Version 9*. Chapters 4.5.2 (Function interface), 10.2 (Lists), 10.3 (Maps), 10.6 (Streams), 12.3 (Parallelism).
- Oracle (n.d.). *Aggregate Operations* (The Java™ Tutorials).

---

## License

This project is created for **educational purposes** – to illustrate functional programming and the Stream API in Java. You are free to use, modify, and distribute it for learning and teaching.

---
