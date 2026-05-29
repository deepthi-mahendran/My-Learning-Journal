# Library Management System (GUI)

A complete Java Swing desktop application for managing a library’s book inventory, handling borrow/return transactions, and keeping a searchable history of all actions with persistent file storage.

---

## Overview

The **Library Management System** provides librarians and staff with a graphical interface to:

- Add, delete, and update book quantities
- Borrow and return copies (with quantity control)
- Search/filter books by title or author
- View a complete, timestamped history of all borrow/return actions
- Automatically save and restore inventory and history using serialized files

The application is built with **Java Swing** and uses in‑memory collections (`HashMap`, `ArrayList`) plus file‑based persistence.

---

## Features

### Inventory Management
- **Add books** – Title, author, quantity (increments existing entries)
- **Borrow books** – Reduce quantity; prevents borrowing more than available
- **Return books** – Increase quantity
- **Delete books** – Remove entire book entry (all copies)
- **Search & filter** – Real‑time search by title or author
- **Sortable table** – Click column headers to sort

### Transaction History
- Automatic recording of every borrow/return with:
  - Timestamp (down to the second)
  - Transaction type (`Borrow` or `Return`)
  - Book title and author
  - Quantity involved
- History persists across sessions
- Clear all history from the GUI

### Persistence
- Library inventory saved to `library_data.ser`
- Transaction history saved to `transactions.ser`
- Automatic save on exit, manual save/load via menu

### User Interface
- Tabbed layout (Inventory / History)
- Status bar with feedback messages
- Responsive table with row filtering and sorting
- Confirmation dialogs for destructive actions

---

## Project Structure

All code is contained in a single file (`LibrarySystemGUI.java`) for simplicity. The classes are:

| Class                    | Responsibility                                                       |
|--------------------------|----------------------------------------------------------------------|
| `LibrarySystemGUI`       | Main JFrame – builds UI, handles events, links managers              |
| `Book`                   | Model – title, author, quantity, and a composite key                 |
| `Transaction`            | Model – timestamp, type, book details, quantity                      |
| `TransactionManager`     | Stores/loads `Transaction` list to/from `transactions.ser`           |
| `LibraryManager`         | Stores/loads `Book` map to/from `library_data.ser` and core logic    |

No external libraries – only standard Java (Swing, serialization, collections).

---

## Requirements

- **Java** – JDK 8 or higher (Swing is included)
- **No additional dependencies**

---

## Compilation and Execution

### Compile

```bash
javac LibrarySystemGUI.java
```

### Run

```bash
java LibrarySystemGUI
```

On first run, the data files do not exist – the application starts with an empty library and empty history.

---

## Usage Guide

When the application starts, you see a window with two tabs: **Book Inventory** and **Transaction History**, plus a menu bar.

### Inventory Tab

#### Add a Book
1. Fill in **Title**, **Author**, and **Quantity** (positive integer)
2. Click **Add Book**
   - If the book already exists, the quantity is **added** to the existing stock.
   - A status message confirms the update.

#### Borrow a Book
1. Select a book row in the table
2. Click **Borrow Selected Book**
3. Enter the number of copies to borrow (must be ≤ available quantity)
4. On success:
   - The table updates the quantity
   - A transaction is recorded in the History tab
   - Status bar shows the new remaining stock

#### Return a Book
1. Select a book row
2. Click **Return Selected Book**
3. Enter the number of copies to return
4. On success: quantity increases, transaction recorded

#### Delete a Book
1. Select a book row
2. Click **Delete Selected Book**
3. Confirm deletion – the entire book entry is removed (all copies)

#### Search / Filter
- Type in the **Search** box (above the table) to filter by title or author (case‑insensitive)
- Click **Clear** to reset the filter

#### Refresh List
- Use the **Refresh List** button to reload from the in‑memory library (useful after manual file loads)

### Transaction History Tab

- Displays all borrow/return actions in reverse chronological order?  
  (Newest transactions appear at the bottom; the table scrolls automatically to show the last entry)
- **Clear All History** button – removes all records after confirmation (cannot be undone)

### Menu Bar Options

- **File**
  - **Save Library & History** – manually persist current state to disk
  - **Load Library & History** – reload from previously saved files (overwrites current data)
  - **Exit** – saves automatically then quits
- **Help**
  - **About** – shows application information

The **Status Bar** at the bottom provides feedback for every action.

---

## Data Persistence

The application uses Java serialization (`.ser` files) saved in the **current working directory**:

| File                 | Content                                   |
|----------------------|-------------------------------------------|
| `library_data.ser`   | `HashMap<String, Book>` where key = `title|author` (lowercase) |
| `transactions.ser`   | `ArrayList<Transaction>`                  |

- **Auto‑save** on normal exit (window close or Exit menu)
- **Manual save/load** via the File menu

If the files are missing, the application starts with empty data. Deleting the files resets everything.

---

## Error Handling

The system gracefully handles common errors with user‑friendly dialogs or status messages:

| Scenario                                   | Response                                                 |
|--------------------------------------------|----------------------------------------------------------|
| Adding a book with empty fields / invalid quantity | Dialog: "All fields are required" / "Positive integer" |
| Borrowing with no selection                | Warning dialog: "Please select a book"                   |
| Borrowing more than available copies       | Error dialog: "Insufficient copies. Only X available."   |
| Entering non‑numeric quantity              | Dialog: "Enter a positive integer"                       |
| Returning a deleted / non‑existent book    | (Should not happen from table, but guard exists)         |
| File corruption during load                | Silently ignored (starts empty) – errors printed to stderr |

---

## Example Workflow

1. **Add two books**
   - `"The Hobbit"`, `"J.R.R. Tolkien"`, quantity `5`
   - `"1984"`, `"George Orwell"`, quantity `3`

2. **Search** – type `"Tolkien"` → only *The Hobbit* appears.

3. **Borrow** – select *The Hobbit*, click Borrow, enter `2`  
   → Quantity becomes `3`, status shows `Success: Borrowed 2...`

4. **Switch to History tab** – see a new row:  
   `2025-05-28 14:35:22 | Borrow | The Hobbit | J.R.R. Tolkien | 2`

5. **Return** – select *The Hobbit*, click Return, enter `1`  
   → Quantity becomes `4`, history adds a `Return` record.

6. **Delete** – select *1984*, click Delete, confirm → book removed.

7. **Exit** – data saved automatically to `*.ser` files. Next launch restores the state.

---

## Extending the System

The modular design makes it easy to add features:

- **Patron management** – add `Patron` class, link borrows to a patron ID
- **Due dates & fines** – extend `Transaction` with due date, fine amount
- **Export reports** – add menu items to save inventory/history as CSV
- **Database backend** – replace serialization with JDBC (e.g., SQLite)
- **Keyboard shortcuts** – bind `Ctrl+F` to focus search, `Ctrl+N` for new book, etc.
- **Editable table cells** – allow inline quantity editing (with validation)

### Adding a new book field (e.g., `ISBN`)

- Add field to `Book` class
- Update `getKey()` to include ISBN or keep title+author as unique key
- Modify `LibrarySystemGUI` input panel and table model accordingly
- Save/load works automatically because `Book` is `Serializable`

---

## License

This project is created for educational purposes – demonstrating Java Swing, serialization, table filtering, and event‑driven GUI design. You may freely use, modify, and distribute it for learning.

---
