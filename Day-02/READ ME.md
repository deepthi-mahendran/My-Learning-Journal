# Library Management System

A simple command-line library management application written in Java. This system allows librarians or users to add books, borrow copies, return copies, and track inventory using a hash map for efficient storage and retrieval.

## Features

- **Add Books** – Add new books to the library or increase the quantity of existing books
- **Borrow Books** – Borrow available copies; system checks for sufficient stock
- **Return Books** – Return borrowed copies and update the inventory
- **Input Validation** – Handles non‑numeric input, negative quantities, and invalid menu choices
- **Persistent Session** – The library data (book records) remains in memory during the session (until exit)
- **Exception Handling** – Uses `try-catch` blocks to prevent crashes from invalid user input

## Prerequisites

- **Java Development Kit (JDK)** 8 or higher  
  Verify installation:
  ```bash
  java -version
  ```

## How to Run

1. **Save the file**  
   Ensure the file name is `LibrarySystem.java` (the public class name).

2. **Compile the program**  
   Open a terminal in the folder containing the file and run:
   ```bash
   javac LibrarySystem.java
   ```

3. **Run the application**  
   ```bash
   java LibrarySystem
   ```

## Example Session

```
--- Library Management System ---
1. Add Books
2. Borrow Books
3. Return Books
4. Exit
Enter your choice: 1

--- Add Books ---
Enter Book Title: Clean Code
Enter Author: Robert C. Martin
Enter Quantity: 5
Success: Added 'Clean Code' by Robert C. Martin with quantity 5.

--- Library Management System ---
Enter your choice: 2

--- Borrow Books ---
Enter Book Title: Clean Code
Enter number of books to borrow: 2
Success: Borrowed 2 copies of 'Clean Code'. Remaining copies: 3

--- Library Management System ---
Enter your choice: 4

Exiting Library System. Goodbye!
```

## Code Structure

| Component | Purpose |
|-----------|---------|
| `Book` class | Encapsulates title, author, and quantity for each book |
| `library` (HashMap) | Stores books with title as key – allows O(1) average‑time lookups |
| `displayMenu()` | Prints the main menu |
| `getUserChoice()` | Reads and validates menu input; handles `InputMismatchException` |
| `addBooks()` | Adds a new book or updates quantity of an existing book |
| `borrowBooks()` | Decreases book quantity if enough copies exist |
| `returnBooks()` | Increases book quantity after return |
| Main loop (`while(true)`) | Keeps the program running until user chooses Exit |

## Error Handling

| Scenario | Handling |
|----------|----------|
| User enters letters instead of a number | `InputMismatchException` caught, error message shown, program continues |
| User enters a negative or zero quantity | Program rejects the value with a clear message |
| User selects an invalid menu option (not 1‑4) | Default case prints an error and loops again |
| Trying to borrow a non‑existent book | Error message: book not found |
| Borrowing more copies than available | Error message: insufficient stock |

## Possible Enhancements

- **List all books** – Add a menu option to display current inventory
- **Persistent storage** – Save library data to a file and load it at startup
- **Search by author** – Enable searching for books by author name
- **User borrowing history** – Track which user borrowed which book
- **GUI version** – Convert the console app to a graphical interface

## References

This project was adapted from object‑oriented principles discussed in Eck (2022) and common exception‑handling patterns (Coding with John, 2020).

---

**Enjoy managing your library!**
