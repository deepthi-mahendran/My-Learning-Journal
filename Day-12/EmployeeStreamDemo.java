// Employee Management System
// Demonstrates Function interface and Stream API (Eck, 2022, Chapters 4.5.2, 10.6)
// Author: Student - Developer
// Date: 6/1/2026

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents an Employee with validation.
 * Data integrity follows the principle of fail-fast programming.
 */
class Employee {
    private final String name;
    private final int age;
    private final String department;
    private final double salary;

    public Employee(String name, int age, String department, double salary) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");
        if (age < 18 || age > 100)
            throw new IllegalArgumentException("Age must be 18-100.");
        if (department == null || department.trim().isEmpty())
            throw new IllegalArgumentException("Department cannot be empty.");
        if (salary < 0)
            throw new IllegalArgumentException("Salary cannot be negative.");
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', age=%d, department='%s', salary=%.2f}",
                name, age, department, salary);
    }
}

public class EmployeeStreamDemo {
    // Function interface implementation (Eck, 2022, 4.5.2)
    private static final Function<Employee, String> nameDepartmentMapper =
            emp -> emp.getName() + " - " + emp.getDepartment();

    public static void main(String[] args) {
        System.out.println("=== Employee Management System ===");
        System.out.println("Using Function interface and Stream API (Java 8+)\n");

        // 1. Read dataset and store in a collection (ArrayList)
        List<Employee> employees = loadEmployeeDataset();   // source: Eck, 2022, Ch. 10.2

        System.out.println("--- Initial Dataset ---");
        employees.forEach(System.out::println);
        System.out.println("\nTotal employees: " + employees.size());

        // 2. Function interface: map Employee -> concatenated string
        System.out.println("\n--- Function Interface (map) ---");
        employees.stream()
                .map(nameDepartmentMapper)
                .limit(5)
                .forEach(System.out::println);

        // 3. Generate a new collection using streams (Eck, 2022, 10.6)
        List<String> nameDepartmentList = employees.stream()
                .map(nameDepartmentMapper)
                .collect(Collectors.toList());

        System.out.println("\n--- New Collection (size: " + nameDepartmentList.size() + ") ---");
        nameDepartmentList.stream().limit(5).forEach(System.out::println);

        // 4. Average salary using built-in stream functions (Oracle, n.d., "Aggregate Operations")
        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.printf("\n--- Average Salary (All Employees) ---\n$%,.2f%n", averageSalary);

        // 5. Filter by age threshold (Predicate functional interface)
        int ageThreshold = 30;
        Predicate<Employee> ageFilter = emp -> emp.getAge() > ageThreshold;
        List<Employee> seniorEmployees = employees.stream()
                .filter(ageFilter)
                .collect(Collectors.toList());

        double avgSeniorSalary = seniorEmployees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.printf("\n--- Employees above age %d ---\nCount: %d\nAverage Salary: $%,.2f%n",
                ageThreshold, seniorEmployees.size(), avgSeniorSalary);

        // === Additional Features ===
        // 6. Department-wise grouping (Eck, 2022, 10.3 - Maps)
        System.out.println("\n=== Bonus: Department Analysis ===");
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));
        avgSalaryByDept.forEach((dept, avgSal) ->
                System.out.printf("%-12s | $%,.2f%n", dept, avgSal));

        // 7. Parallel stream performance (Coding with John, 2021; Eck, 2022, Ch. 12.3)
        System.out.println("\n=== Bonus: Parallel Stream Benchmark ===");
        long startSeq = System.nanoTime();
        double seqAvg = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0);
        long endSeq = System.nanoTime();
        long startPar = System.nanoTime();
        double parAvg = employees.parallelStream().mapToDouble(Employee::getSalary).average().orElse(0);
        long endPar = System.nanoTime();
        System.out.printf("Sequential: %,d ns%nParallel:   %,d ns%nSpeedup:    %.2fx%n",
                (endSeq - startSeq), (endPar - startPar),
                (double)(endSeq - startSeq) / (endPar - startPar));

        // 8. Top 3 highest paid employees (short-circuiting: limit())
        System.out.println("\n=== Bonus: Top 3 Earners ===");
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(3)
                .forEach(emp -> System.out.printf("%-20s | %-12s | $%,.2f%n",
                        emp.getName(), emp.getDepartment(), emp.getSalary()));
    }

    /**
     * Loads sample dataset. In production, this would read from a file or database.
     * (Eck, 2022, Chapter 10.2 - Lists and Sets)
     */
    private static List<Employee> loadEmployeeDataset() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice Johnson", 29, "Engineering", 85000));
        employees.add(new Employee("Bob Smith", 35, "Marketing", 72000));
        employees.add(new Employee("Carol Davis", 42, "Engineering", 110000));
        employees.add(new Employee("David Brown", 28, "Sales", 58000));
        employees.add(new Employee("Eva Wilson", 38, "Marketing", 79000));
        employees.add(new Employee("Frank Miller", 45, "Engineering", 125000));
        employees.add(new Employee("Grace Lee", 31, "Human Resources", 65000));
        employees.add(new Employee("Henry Taylor", 27, "Sales", 62000));
        employees.add(new Employee("Ivy Clark", 53, "Engineering", 145000));
        employees.add(new Employee("Jack White", 33, "Human Resources", 68000));
        return employees;
    }
}

