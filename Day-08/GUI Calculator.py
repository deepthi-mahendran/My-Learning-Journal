import tkinter as tk
from tkinter import messagebox, font

class ModernCalculator:
    def __init__(self, root):
        self.root = root
        self.root.title("✨ Modern Calculator")
        self.root.geometry("400x600")
        self.root.resizable(False, False)
        self.root.configure(bg="#1e1e2f")

        # Colors
        self.bg_color = "#1e1e2f"
        self.display_bg = "#2d2d42"
        self.btn_bg = "#2d2d42"
        self.btn_hover = "#3e3e5e"
        self.special_bg = "#4a4a6a"
        self.equal_bg = "#6c5ce7"
        self.equal_hover = "#7e6ef0"
        self.text_color = "#ffffff"
        self.op_color = "#a29bfe"

        self.expression = ""
        self.result_shown = False

        # Create custom fonts
        self.display_font = font.Font(family="Segoe UI", size=28, weight="normal")
        self.btn_font = font.Font(family="Segoe UI", size=16, weight="bold")

        self.create_widgets()
        self.setup_layout()

    def create_widgets(self):
        # Display Entry
        self.display = tk.Entry(
            self.root,
            font=self.display_font,
            bg=self.display_bg,
            fg=self.text_color,
            bd=0,
            justify="right",
            relief=tk.FLAT,
            insertbackground=self.text_color,
            highlightthickness=0
        )
        self.display.grid(row=0, column=0, columnspan=4, sticky="nsew", padx=20, pady=(30, 20))

        # Buttons data (text, row, col, span)
        buttons = [
            ("C", 1, 0, 1, self.special_bg, self.op_color, self.clear),
            ("%", 1, 1, 1, self.btn_bg, self.op_color, lambda: self.add_to_expression("%")),
            ("/", 1, 2, 1, self.btn_bg, self.op_color, lambda: self.add_to_expression("/")),
            ("*", 1, 3, 1, self.btn_bg, self.op_color, lambda: self.add_to_expression("*")),

            ("7", 2, 0, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("7")),
            ("8", 2, 1, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("8")),
            ("9", 2, 2, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("9")),
            ("-", 2, 3, 1, self.btn_bg, self.op_color, lambda: self.add_to_expression("-")),

            ("4", 3, 0, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("4")),
            ("5", 3, 1, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("5")),
            ("6", 3, 2, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("6")),
            ("+", 3, 3, 1, self.btn_bg, self.op_color, lambda: self.add_to_expression("+")),

            ("1", 4, 0, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("1")),
            ("2", 4, 1, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("2")),
            ("3", 4, 2, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression("3")),
            ("=", 4, 3, 1, self.equal_bg, self.text_color, self.evaluate),

            ("0", 5, 0, 2, self.btn_bg, self.text_color, lambda: self.add_to_expression("0")),
            (".", 5, 2, 1, self.btn_bg, self.text_color, lambda: self.add_to_expression(".")),
        ]

        self.buttons = []
        for (text, row, col, span, bg, fg, cmd) in buttons:
            btn = tk.Button(
                self.root,
                text=text,
                font=self.btn_font,
                bg=bg,
                fg=fg,
                bd=0,
                relief=tk.FLAT,
                activebackground=self.btn_hover,
                activeforeground=self.text_color,
                command=cmd,
                cursor="hand2"
            )
            btn.grid(row=row, column=col, columnspan=span, sticky="nsew", padx=8, pady=8)
            self.buttons.append(btn)
            # Bind hover effects
            btn.bind("<Enter>", lambda e, b=btn, bg=bg: self.on_enter(b, bg))
            btn.bind("<Leave>", lambda e, b=btn, bg=bg: self.on_leave(b, bg))

        # Special handling for '=' hover different color
        for btn in self.buttons:
            if btn["text"] == "=":
                btn.bind("<Enter>", lambda e, b=btn: self.on_enter_equal(b))
                btn.bind("<Leave>", lambda e, b=btn: self.on_leave_equal(b))

    def on_enter(self, button, original_bg):
        button.config(bg=self.btn_hover)

    def on_leave(self, button, original_bg):
        button.config(bg=original_bg)

    def on_enter_equal(self, button):
        button.config(bg=self.equal_hover)

    def on_leave_equal(self, button):
        button.config(bg=self.equal_bg)

    def setup_layout(self):
        for i in range(6):
            self.root.grid_rowconfigure(i, weight=1)
        for j in range(4):
            self.root.grid_columnconfigure(j, weight=1)

    def add_to_expression(self, value):
        if self.result_shown:
            self.expression = ""
            self.result_shown = False
        self.expression += value
        self.update_display()

    def clear(self):
        self.expression = ""
        self.result_shown = False
        self.update_display()

    def evaluate(self):
        try:
            # Safe eval: allow only math operations
            result = eval(self.expression, {"__builtins__": None}, {})
            if isinstance(result, float) and result.is_integer():
                result = int(result)
            self.expression = str(result)
            self.result_shown = True
            self.update_display()
        except ZeroDivisionError:
            messagebox.showerror("Math Error", "Cannot divide or modulo by zero")
            self.clear()
        except Exception:
            messagebox.showerror("Syntax Error", "Invalid expression")
            self.clear()

    def update_display(self):
        self.display.delete(0, tk.END)
        self.display.insert(0, self.expression)

if __name__ == "__main__":
    root = tk.Tk()
    app = ModernCalculator(root)
    root.mainloop()
