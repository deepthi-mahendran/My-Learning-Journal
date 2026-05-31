# TaskFlow Pro – Modern To‑Do List App

A clean, professional to‑do list application built with Python and **CustomTkinter**. It offers a modern dark/light mode interface, persistent task storage, and intuitive task management with checkboxes and per‑task removal.

## Overview

**TaskFlow Pro** is a desktop to‑do list manager that helps you keep track of your daily tasks. Tasks are stored locally in a text file, so your list persists between sessions. The interface is built with [CustomTkinter](https://github.com/TomSchimansky/CustomTkinter), a modern UI library for Python that extends Tkinter with stylish, responsive widgets and built‑in appearance modes.

---

## Features

- **Add tasks** – Type a task and press `Enter` or click the **Add Task** button.
- **Mark complete / incomplete** – Click the checkbox next to a task to toggle its status.
- **Remove tasks** – Each task has a red **✖** button for immediate deletion.
- **Persistent storage** – Tasks are automatically saved to `tasks.txt` in the same folder.
- **Dark / Light mode** – Toggle between themes with a switch (defaults to dark).
- **Status bar** – Shows total tasks, completed, and pending counts in real time.
- **Responsive layout** – Window is centered on launch and can be resized (minimum 600×450).
- **Scrollable task list** – Handles many tasks without overflowing.

---

## Requirements

- **Python 3.7+**
- **CustomTkinter** library

> CustomTkinter is not included in the standard Python distribution – install it separately.

---

## Installation

1. **Clone or download** the file `todo-list-app.py`.

2. **Install CustomTkinter** using pip:

   ```bash
   pip install customtkinter
   ```

3. (Optional) Verify installation:

   ```bash
   python -c "import customtkinter; print(customtkinter.__version__)"
   ```

---

## Running the App

Open a terminal in the folder containing `todo-list-app.py` and run:

```bash
python todo-list-app.py
```

No additional command‑line arguments are needed.

---

## Usage Guide

| Action                    | How to do it                                                                 |
|---------------------------|------------------------------------------------------------------------------|
| **Add a task**            | Type into the input field and press `Enter` or click the **➕ Add Task** button. |
| **Mark a task complete**  | Click the checkbox next to the task – the text turns gray and italic.       |
| **Mark a task incomplete**| Click the same checkbox again – it returns to normal styling.               |
| **Delete a task**         | Click the red **✖** button on the right side of the task row.               |
| **Toggle appearance**     | Flip the **Dark Mode** switch in the top‑right corner.                      |
| **See statistics**        | Look at the status bar at the bottom for counts.                           |

Tasks are saved automatically after every add, toggle, or delete.

---

## File Structure

```
your-folder/
├── todo-list-app.py      # Main application code
└── tasks.txt             # Auto‑created file (stores all tasks)
```

**`tasks.txt` format** (human‑readable):

```
[ ] Write documentation
[x] Review pull requests
[ ] Prepare meeting slides
```

- `[ ]` = pending task
- `[x]` = completed task

If the file is missing when the app starts, it is created automatically on first save.

---

## Customization

You can tweak the app by editing constants at the top of the file:

```python
ctk.set_appearance_mode("dark")      # Change to "light" or "system"
ctk.set_default_color_theme("blue")  # Try "green", "dark-blue", or "purple"
TASKS_FILE = "tasks.txt"             # Change filename if needed
```

The window size is set in `self.window.geometry("700x550")` – modify as you like.

---

## Known Limitations

- **No strikethrough text** – CustomTkinter does not natively support strikethrough. Completed tasks are shown in gray italics instead.
- **No keyboard shortcuts** (e.g., `Ctrl+D` to delete) – but the interface is fully clickable.
- **No due dates or priorities** – This is a minimal to‑do list; advanced features would require extra development.
- **Single user** – Tasks are stored locally; no cloud sync or multi‑user support.

These are intentional design choices for a lightweight, focused app.

---
## Screenshots
<img width="873" height="712" alt="Screenshot 2026-05-31 205022" src="https://github.com/user-attachments/assets/12ebe9b4-b375-445e-9847-a40a377bbc9c" />

---

<img width="862" height="713" alt="Screenshot 2026-05-31 205029" src="https://github.com/user-attachments/assets/d9c2aa54-bc25-49fd-a239-d610fdcdf5cc" />

---
## Extending the App

The code is modular and easy to enhance:

- **Add due dates** – Extend the task model to a class with `due_date` and display it in each row.
- **Add edit functionality** – Double‑click a task label to open an inline edit field.
- **Add categories or tags** – Use a dropdown next to the add field.
- **Export tasks** – Add a button to save the list as CSV or JSON.
- **Keyboard shortcuts** – Bind keys (e.g., `<Control-n>` for new task) using `self.window.bind()`.
- **Search/filter** – Add a search entry to filter the task list dynamically.

Because tasks are stored as a simple list of tuples (`(status, text)`), you can easily replace the storage with a database (SQLite) for more advanced queries.

---

## License

This project is for educational purposes – demonstrating CustomTkinter GUI development, persistent storage, and event‑driven programming. You may freely use, modify, and distribute it.
