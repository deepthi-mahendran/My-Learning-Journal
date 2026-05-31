import customtkinter as ctk
import os

# Set appearance and theme
ctk.set_appearance_mode("dark")      # "dark", "light", "system"
ctk.set_default_color_theme("blue")  # Themes: "blue", "green", "dark-blue"

TASKS_FILE = "tasks.txt"

class ProfessionalTodoApp:
    def __init__(self):
        self.window = ctk.CTk()
        self.window.title("TaskFlow Pro")
        self.window.geometry("700x550")
        self.window.minsize(600, 450)

        # Center the window
        self.center_window()

        # Load tasks
        self.tasks = []
        self.load_tasks()

        # Build UI
        self.create_widgets()
        self.update_task_list()

    def center_window(self):
        self.window.update_idletasks()
        width = 700
        height = 550
        x = (self.window.winfo_screenwidth() // 2) - (width // 2)
        y = (self.window.winfo_screenheight() // 2) - (height // 2)
        self.window.geometry(f"{width}x{height}+{x}+{y}")

    def create_widgets(self):
        # Main container with padding
        self.main_frame = ctk.CTkFrame(self.window, fg_color="transparent")
        self.main_frame.pack(fill="both", expand=True, padx=20, pady=20)

        # Header
        header_frame = ctk.CTkFrame(self.main_frame, fg_color="transparent")
        header_frame.pack(fill="x", pady=(0, 20))

        title = ctk.CTkLabel(
            header_frame,
            text="📋 TaskFlow Pro",
            font=ctk.CTkFont(size=28, weight="bold")
        )
        title.pack(side="left")

        # Dark/Light mode toggle
        self.mode_toggle = ctk.CTkSwitch(
            header_frame,
            text="Dark Mode",
            command=self.toggle_mode,
            progress_color="#4a90e2",
            button_color="#4a90e2"
        )
        self.mode_toggle.pack(side="right")
        self.mode_toggle.select()  # start in dark mode (selected)

        # Task list (scrollable)
        self.task_frame = ctk.CTkScrollableFrame(
            self.main_frame,
            fg_color="transparent",
            height=320
        )
        self.task_frame.pack(fill="both", expand=True, pady=(0, 15))

        # Add new task area
        add_frame = ctk.CTkFrame(self.main_frame, fg_color="transparent")
        add_frame.pack(fill="x", pady=(0, 10))

        self.task_entry = ctk.CTkEntry(
            add_frame,
            placeholder_text="Write a new task...",
            height=45,
            font=ctk.CTkFont(size=14)
        )
        self.task_entry.pack(side="left", fill="x", expand=True, padx=(0, 10))
        self.task_entry.bind("<Return>", lambda event: self.add_task())

        self.add_btn = ctk.CTkButton(
            add_frame,
            text="➕ Add Task",
            width=120,
            height=45,
            command=self.add_task,
            fg_color="#2ecc71",
            hover_color="#27ae60",
            corner_radius=8
        )
        self.add_btn.pack(side="right")

        # Action buttons (complete / remove) – placed nicely below
        action_frame = ctk.CTkFrame(self.main_frame, fg_color="transparent")
        action_frame.pack(fill="x")

        self.complete_btn = ctk.CTkButton(
            action_frame,
            text="✅ Mark Complete",
            command=self.mark_complete,
            fg_color="#3498db",
            hover_color="#2980b9",
            corner_radius=8
        )
        self.complete_btn.pack(side="left", padx=(0, 10), fill="x", expand=True)

        self.remove_btn = ctk.CTkButton(
            action_frame,
            text="🗑️ Remove Task",
            command=self.remove_task,
            fg_color="#e74c3c",
            hover_color="#c0392b",
            corner_radius=8
        )
        self.remove_btn.pack(side="right", fill="x", expand=True)

        # Status bar
        self.status_label = ctk.CTkLabel(
            self.main_frame,
            text="",
            font=ctk.CTkFont(size=12)
        )
        self.status_label.pack(pady=(10, 0))
        self.update_status()

    def add_task(self):
        task_text = self.task_entry.get().strip()
        if not task_text:
            return
        self.tasks.append(("[ ]", task_text))
        self.task_entry.delete(0, "end")
        self.save_tasks()
        self.update_task_list()

    def remove_task(self):
        selected = None
        # Find which task's checkbox was toggled? No – easier: we store a reference
        # But we need to know which task to remove. We'll use a popup or right-click?
        # For simplicity, I'll implement selection by clicking on the task label.
        # But CustomTkinter checkboxes don't have a "selected" state like listbox.
        # Better approach: each task row has its own "Remove" button? That's cleaner.
        # Let's re-design: each task appears as a row with checkbox (complete), label, and an "X" remove button.
        # That is much more professional and intuitive.
        # I'll rewrite the task display to use a dynamic grid of frames.

    def mark_complete(self):
        # Same issue – need per-task interaction.
        pass

    # We need a better task display method – let's rebuild the task listing with per-task controls

    def rebuild_task_list(self):
        # Clear the scrollable frame
        for widget in self.task_frame.winfo_children():
            widget.destroy()

        for idx, (status, text) in enumerate(self.tasks):
            # Create a row frame
            row = ctk.CTkFrame(self.task_frame, fg_color="transparent", corner_radius=0)
            row.pack(fill="x", pady=5)

            # Checkbox for complete/incomplete
            check_var = ctk.BooleanVar(value=(status == "[x]"))
            checkbox = ctk.CTkCheckBox(
                row,
                text="",
                variable=check_var,
                command=lambda i=idx, var=check_var: self.toggle_task(i, var),
                width=30,
                corner_radius=6,
                border_color="#4a90e2",
                checkmark_color="white"
            )
            checkbox.pack(side="left", padx=(0, 10))

            # Task label
            label = ctk.CTkLabel(
                row,
                text=text,
                font=ctk.CTkFont(size=14),
                anchor="w"
            )
            label.pack(side="left", fill="x", expand=True, padx=5)

            # Apply strikethrough effect if completed (CustomTkinter doesn't support strikethrough natively,
            # but we can change color to gray and add a small note – or just keep as is)
            if status == "[x]":
                label.configure(text_color="gray", font=ctk.CTkFont(size=14, slant="italic"))

            # Remove button
            remove_btn = ctk.CTkButton(
                row,
                text="✖",
                width=30,
                height=30,
                fg_color="#e74c3c",
                hover_color="#c0392b",
                corner_radius=15,
                command=lambda i=idx: self.delete_task(i)
            )
            remove_btn.pack(side="right", padx=(5, 0))

        self.update_status()

    def toggle_task(self, index, check_var):
        # Update status based on checkbox
        if check_var.get():
            self.tasks[index] = ("[x]", self.tasks[index][1])
        else:
            self.tasks[index] = ("[ ]", self.tasks[index][1])
        self.save_tasks()
        self.rebuild_task_list()

    def delete_task(self, index):
        del self.tasks[index]
        self.save_tasks()
        self.rebuild_task_list()

    def update_task_list(self):
        # Alias for rebuild
        self.rebuild_task_list()

    def update_status(self):
        total = len(self.tasks)
        completed = sum(1 for s, _ in self.tasks if s == "[x]")
        pending = total - completed
        self.status_label.configure(text=f"{total} total   •   {completed} completed   •   {pending} pending")

    def toggle_mode(self):
        if self.mode_toggle.get() == 1:
            ctk.set_appearance_mode("dark")
        else:
            ctk.set_appearance_mode("light")

    def load_tasks(self):
        if not os.path.exists(TASKS_FILE):
            return
        try:
            with open(TASKS_FILE, "r", encoding="utf-8") as f:
                lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line:
                    if line.startswith("[x]"):
                        self.tasks.append(("[x]", line[3:].strip()))
                    elif line.startswith("[ ]"):
                        self.tasks.append(("[ ]", line[3:].strip()))
                    else:
                        self.tasks.append(("[ ]", line))
        except Exception as e:
            print(f"Load error: {e}")

    def save_tasks(self):
        try:
            with open(TASKS_FILE, "w", encoding="utf-8") as f:
                for status, text in self.tasks:
                    f.write(f"{status} {text}\n")
        except Exception as e:
            print(f"Save error: {e}")

    def run(self):
        self.window.mainloop()

if __name__ == "__main__":
    app = ProfessionalTodoApp()
    app.run()
