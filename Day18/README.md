# Generic Library Catalog System (Java Swing)

A comprehensive Java desktop application that demonstrates the power of **generics** through a library catalog management system. This application allows users to manage different types of library items (Books, DVDs, Magazines) using a single, type-safe generic catalog system with a modern Swing GUI.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Compilation and Execution](#compilation-and-execution)
- [Usage Guide](#usage-guide)
- [Key Components](#key-components)
  - [Generic Classes](#generic-classes)
  - [Type-Specific Details](#type-specific-details)
  - [Swing GUI](#swing-gui)
- [How Generics Work](#how-generics-work)
- [Customization](#customization)
- [Extending the System](#extending-the-system)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## Overview

The **Generic Library Catalog System** is a Java Swing application that demonstrates the practical use of **generics** in a real-world scenario. It allows users to:

- Add, remove, and view library items (Books, DVDs, Magazines).
- Store type-specific details for each item category.
- Use a single generic catalog class that works with any item type.
- Interact through a clean, intuitive Swing interface.

This project is an excellent demonstration of Java's generics feature, showing how to create reusable, type-safe components that work with different data types.

---

## Features

| Feature                     | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| **Generic Catalog Class**   | A single `Catalog<T>` class that works with any item type.                                      |
| **Generic Item Class**      | `LibraryItem<T>` stores common fields and type-specific details.                                 |
| **Three Item Types**        | Books, DVDs, and Magazines with their own unique attributes.                                     |
| **Type-Safe Operations**    | Compile-time type checking prevents mixing item types.                                          |
| **Swing GUI**               | User-friendly interface with dynamic panels for each item type.                                 |
| **Add Items**               | Validate and add items with type-specific details.                                              |
| **Remove Items**            | Remove items by ID with error handling.                                                         |
| **View Catalog**            | Display all items of the selected type with their details.                                      |
| **Dynamic UI**              | Details panel changes based on the selected item type.                                         |
| **Input Validation**        | Validates required fields and numeric inputs.                                                   |
| **Error Handling**          | User-friendly error messages for invalid inputs and missing items.                              |

---

## Technology Stack

- **Java SE** — Core language and standard library.
- **Java Generics** — Type-safe generic classes and methods.
- **Java Swing** — GUI framework for the desktop interface.
- **Java Collections** — `ArrayList`, `List`, and stream operations.

**No external dependencies** — pure Java standard library.

---

## Project Structure

```
src/
├── LibraryItem.java           # Generic item class
├── Catalog.java               # Generic catalog class
├── BookDetails.java           # Book-specific details
├── DVDDetails.java            # DVD-specific details
├── MagazineDetails.java       # Magazine-specific details
└── CatalogGUI.java            # Swing GUI implementation
```

### Class Diagram

```
┌──────────────────────┐
│   LibraryItem<T>     │
├──────────────────────┤
│ - title: String      │
│ - author: String     │
│ - itemID: String     │
│ - details: T         │
├──────────────────────┤
│ + getTitle()         │
│ + getAuthor()        │
│ + getItemID()        │
│ + getDetails()       │
└──────────────────────┘
          ▲
          │ uses
          │
┌──────────────────────┐     ┌──────────────────────┐
│     Catalog<T>       │     │     BookDetails      │
├──────────────────────┤     ├──────────────────────┤
│ - items: List<...>   │     │ - pages: int         │
├──────────────────────┤     │ - publisher: String  │
│ + addItem()          │     ├──────────────────────┤
│ + removeItem()       │     │ + getPages()         │
│ + getItem()          │     │ + getPublisher()     │
│ + getAllItems()      │     └──────────────────────┘
└──────────────────────┘
          │
          │ uses
          ▼
┌──────────────────────┐     ┌──────────────────────┐
│   CatalogGUI         │     │     DVDDetails       │
│   (Swing)            │     ├──────────────────────┤
├──────────────────────┤     │ - director: String   │
│ - bookCatalog        │     │ - duration: int      │
│ - dvdCatalog         │     ├──────────────────────┤
│ - magCatalog         │     │ + getDirector()      │
├──────────────────────┤     │ + getDuration()      │
│ + addItem()          │     └──────────────────────┘
│ + removeItem()       │
│ + viewItems()        │     ┌──────────────────────┐
└──────────────────────┘     │   MagazineDetails    │
                             ├──────────────────────┤
                             │ - issueNumber: int   │
                             │ - publisher: String  │
                             ├──────────────────────┤
                             │ + getIssueNumber()   │
                             │ + getPublisher()     │
                             └──────────────────────┘
```

---

## Requirements

- **Java** — JDK 8 or higher (uses generics and Swing).

**No external dependencies** — pure Java SE.

---

## Compilation and Execution

### 1. Compile all source files

Make sure all `.java` files are in the same directory:

```bash
javac *.java
```

### 2. Run the application

```bash
java CatalogGUI
```

### 3. Using an IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Open the project folder.
2. Run `CatalogGUI.main()`.

---

## Usage Guide

### Step 1: Launch the Application

The main window appears with:

- **Item Information Panel**: Common fields for all items (Type, Title, Author, ID).
- **Type-Specific Details Panel**: Changes based on the selected item type.
- **Action Buttons**: Add, Remove, and View items.
- **Display Area**: Shows the catalog contents.

### Step 2: Select Item Type

Use the **Item Type** dropdown to select:
- **Book** — shows Pages and Publisher fields.
- **DVD** — shows Director and Duration fields.
- **Magazine** — shows Issue Number and Publisher fields.

### Step 3: Add an Item

1. Select the item type.
2. Fill in the common fields: Title, Author, and Item ID.
3. Fill in the type-specific details (e.g., Pages for a Book).
4. Click **Add Item**.
5. A success message confirms the addition, and the display updates.

### Step 4: View Items

Click **View All Items** to display all items of the selected type in the text area.

### Step 5: Remove an Item

1. Click **Remove Item**.
2. Enter the Item ID of the item to remove.
3. Click OK to confirm removal.

---

## Key Components

### Generic Classes

#### `LibraryItem<T>` — Generic Item Class

```java
public class LibraryItem<T> {
    private String title;
    private String author;
    private String itemID;
    private T details;  // Type-specific details

    public LibraryItem(String title, String author, String itemID, T details) {
        // ...
    }

    public T getDetails() { return details; }
    // ...
}
```

**Purpose:** Holds common fields for all library items and a type-specific `details` field.

**Generic Parameter:**
- `T` — The type of details (e.g., `BookDetails`, `DVDDetails`, `MagazineDetails`).

#### `Catalog<T>` — Generic Catalog Class

```java
public class Catalog<T> {
    private List<LibraryItem<T>> items;

    public void addItem(LibraryItem<T> item) { ... }
    public void removeItem(String itemID) { ... }
    public Optional<LibraryItem<T>> getItem(String itemID) { ... }
    public List<LibraryItem<T>> getAllItems() { ... }
}
```

**Purpose:** Manages a collection of `LibraryItem<T>` objects.

**Generic Parameter:**
- `T` — The type of details the catalog stores.

### Type-Specific Details

#### `BookDetails`

```java
public class BookDetails {
    private int pages;
    private String publisher;
    // ...
}
```

#### `DVDDetails`

```java
public class DVDDetails {
    private String director;
    private int duration; // minutes
    // ...
}
```

#### `MagazineDetails`

```java
public class MagazineDetails {
    private int issueNumber;
    private String publisher;
    // ...
}
```

### Swing GUI (`CatalogGUI`)

The GUI uses three separate `Catalog` instances:

```java
private final Catalog<BookDetails> bookCatalog = new Catalog<>();
private final Catalog<DVDDetails> dvdCatalog = new Catalog<>();
private final Catalog<MagazineDetails> magazineCatalog = new Catalog<>();
```

**Dynamic UI:** The details panel uses a `CardLayout` to switch between the three type-specific panels.

**Key Methods:**
- `addItem()` — Validates input and adds the appropriate item type.
- `removeItem()` — Prompts for ID and removes the item.
- `viewItems()` — Displays all items of the selected type.

---

## How Generics Work

### Type Safety

Without generics, you would need separate catalog classes for each item type, or use `Object` and lose type safety:

```java
// Without generics (not type-safe)
public class Catalog {
    private List<LibraryItem> items; // items are Objects
    // No compile-time type checking
}

// With generics (type-safe)
Catalog<BookDetails> bookCatalog = new Catalog<>();
// Can only store LibraryItem<BookDetails>
```

### Compile-Time Checking

The compiler ensures that you cannot mix item types:

```java
// This will cause a compile error
BookDetails bookDetails = new BookDetails(300, "Penguin");
LibraryItem<BookDetails> bookItem = new LibraryItem<>(title, author, id, bookDetails);
dvdCatalog.addItem(bookItem); // ERROR: cannot add BookDetails to Catalog<DVDDetails>
```

### Type Inference

Java infers the generic type from the context:

```java
Catalog<BookDetails> bookCatalog = new Catalog<>(); // Diamond operator
```

### Wildcards

The `viewItems()` method uses a wildcard to accept any `Catalog` type:

```java
private void viewItems(ActionEvent e) {
    String type = (String) typeCombo.getSelectedItem();
    List<? extends LibraryItem<?>> items; // Wildcard for any item type
    // ...
}
```

---

## Customization

### Adding a New Item Type

1. Create a new details class (e.g., `AudiobookDetails`).

```java
public class AudiobookDetails {
    private int runtime;
    private String narrator;
    // Constructor, getters, toString()
}
```

2. Add a new catalog to the GUI:

```java
private final Catalog<AudiobookDetails> audiobookCatalog = new Catalog<>();
```

3. Add a new case to the type dropdown and update `addItem()`:

```java
case "Audiobook":
    int runtime = Integer.parseInt(runtimeField.getText().trim());
    String narrator = narratorField.getText().trim();
    AudiobookDetails aDetails = new AudiobookDetails(runtime, narrator);
    LibraryItem<AudiobookDetails> audioItem = new LibraryItem<>(title, author, id, aDetails);
    audiobookCatalog.addItem(audioItem);
    break;
```

4. Add a new details panel to the `CardLayout`.

### Changing the ID Format

Modify the validation in `addItem()`:

```java
if (!id.matches("^[A-Z]{3}-\\d{4}$")) {
    JOptionPane.showMessageDialog(this, "ID must be in format ABC-1234.");
    return;
}
```

### Adding Search Functionality

Add a search method to `Catalog`:

```java
public List<LibraryItem<T>> searchByTitle(String keyword) {
    return items.stream()
        .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
        .collect(Collectors.toList());
}
```

### Persisting Data (Save/Load)

Add serialization to `Catalog`:

```java
public void saveToFile(String filename) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
        oos.writeObject(items);
    }
}

@SuppressWarnings("unchecked")
public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
        items = (List<LibraryItem<T>>) ois.readObject();
    }
}
```

---

## Extending the System

### Add a Search Feature

- Add a search field and button to the GUI.
- Use `Catalog.getItem()` or a custom search method.
- Display search results in a separate area or dialog.

### Add Data Persistence

- Implement `Serializable` in `LibraryItem` and detail classes.
- Add "Save" and "Load" buttons to the GUI.
- Use `ObjectOutputStream` and `ObjectInputStream`.

### Add Validation Rules

- Validate ID format (e.g., "B001", "D002").
- Validate year ranges, price ranges, etc.

### Add More Item Types

- Add `Audiobook`, `EBook`, `Journal`, `Newspaper`, etc.
- Each with its own unique details.

### Add a Borrowing System

- Add `isBorrowed`, `borrower`, `dueDate` fields.
- Add borrow/return functionality.

### Add Categories/Tags

- Add a `category` field to `LibraryItem`.
- Allow filtering by category.

---

## Troubleshooting

| Issue                        | Solution                                                                 |
|------------------------------|--------------------------------------------------------------------------|
| **Compilation errors**       | Ensure all `.java` files are in the same directory.                     |
| **"Class not found"**        | Use `javac *.java` to compile all files.                                |
| **Invalid number format**    | Check that numeric fields (Pages, Duration, Issue) contain valid integers. |
| **Item not found**           | Verify the Item ID exists in the catalog.                               |
| **GUI not displaying**       | Ensure `SwingUtilities.invokeLater()` is used to launch the GUI.        |
| **ClassCastException**       | Check that you're using the correct generic type for each catalog.      |
| **NoSuchElementException**   | Check for empty Optional in `getItem()` calls.                          |

---

## Generics in Action: Code Examples

### Creating a Book Item

```java
BookDetails details = new BookDetails(350, "HarperCollins");
LibraryItem<BookDetails> book = new LibraryItem<>(
    "The Great Gatsby",
    "F. Scott Fitzgerald",
    "B001",
    details
);
bookCatalog.addItem(book);
```

### Creating a DVD Item

```java
DVDDetails details = new DVDDetails("Christopher Nolan", 148);
LibraryItem<DVDDetails> dvd = new LibraryItem<>(
    "Inception",
    "Christopher Nolan",
    "D001",
    details
);
dvdCatalog.addItem(dvd);
```

### Retrieving Type-Safe Details

```java
Optional<LibraryItem<BookDetails>> book = bookCatalog.getItem("B001");
book.ifPresent(item -> {
    BookDetails details = item.getDetails();
    System.out.println("Pages: " + details.getPages());
    System.out.println("Publisher: " + details.getPublisher());
});
```

---

## Key Learning Points

This project demonstrates:

1. **Generics** — Creating type-safe, reusable generic classes.
2. **Encapsulation** — Private fields with public getters/setters.
3. **Swing GUI** — Building dynamic user interfaces with `CardLayout`.
4. **Collections** — Using `ArrayList` and `List` with generics.
5. **Optional** — Using `Optional` for safe null handling.
6. **Stream API** — Filtering and finding items with streams.
7. **Event-Driven Programming** — Action listeners for buttons and dropdowns.

---

## License

This project is created for **educational purposes** — to demonstrate Java generics, Swing GUI development, and object-oriented design. You are free to use, modify, and distribute this code for learning.

---

**Author:** Student Developer  
**Version:** 1.0  
**Date:** June 2026
