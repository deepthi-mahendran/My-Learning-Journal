import secrets
import string
import customtkinter as ctk
from tkinter import messagebox

# Set appearance mode and color theme for a beautiful modern look
ctk.set_appearance_mode("dark")       # Options: "dark", "light", "system"
ctk.set_default_color_theme("blue")   # Options: "blue", "green", "dark-blue"


class PasswordGeneratorApp(ctk.CTk):
    def __init__(self):
        super().__init__()

        # Window configuration
        self.title("🔐 Secure Password Generator")
        self.geometry("600x650")
        self.minsize(500, 580)
        self.resizable(True, True)

        # Center the window on the screen
        self.center_window()

        # Variables for user selections
        self.length_var = ctk.IntVar(value=16)          # Password length
        self.uppercase_var = ctk.BooleanVar(value=True) # Include A-Z
        self.lowercase_var = ctk.BooleanVar(value=True) # Include a-z
        self.digits_var = ctk.BooleanVar(value=True)    # Include 0-9
        self.symbols_var = ctk.BooleanVar(value=True)   # Include symbols

        # Build the user interface
        self.create_widgets()

        # Generate an initial password on startup
        self.generate_password()

    def center_window(self):
        """Center the window on the screen."""
        self.update_idletasks()
        width = self.winfo_width()
        height = self.winfo_height()
        x = (self.winfo_screenwidth() // 2) - (width // 2)
        y = (self.winfo_screenheight() // 2) - (height // 2)
        self.geometry(f"{width}x{height}+{x}+{y}")

    def create_widgets(self):
        """Create and arrange all GUI components."""
        # Main container with padding
        main_frame = ctk.CTkFrame(self, corner_radius=15)
        main_frame.pack(fill="both", expand=True, padx=25, pady=25)

        # Title
        title_label = ctk.CTkLabel(
            main_frame,
            text="Password Generator",
            font=ctk.CTkFont(size=28, weight="bold"),
            text_color="#2ecc71"
        )
        title_label.pack(pady=(10, 20))

        # ---- Password Length Section ----
        length_frame = ctk.CTkFrame(main_frame, fg_color="transparent")
        length_frame.pack(fill="x", pady=10)

        ctk.CTkLabel(
            length_frame,
            text="Password Length:",
            font=ctk.CTkFont(size=16, weight="bold")
        ).pack(anchor="w", padx=5)

        # Slider and entry row
        slider_entry_frame = ctk.CTkFrame(length_frame, fg_color="transparent")
        slider_entry_frame.pack(fill="x", pady=(5, 0))

        # Slider for length
        self.length_slider = ctk.CTkSlider(
            slider_entry_frame,
            from_=8,
            to=50,
            number_of_steps=42,
            variable=self.length_var,
            command=self.update_length_entry
        )
        self.length_slider.pack(side="left", fill="x", expand=True, padx=(0, 15))

        # Entry for manual length input
        self.length_entry = ctk.CTkEntry(
            slider_entry_frame,
            width=70,
            justify="center",
            font=ctk.CTkFont(size=14)
        )
        self.length_entry.pack(side="right")
        self.length_entry.insert(0, str(self.length_var.get()))
        self.length_entry.bind("<Return>", self.on_length_entry_return)
        self.length_entry.bind("<FocusOut>", self.on_length_entry_return)

        # ---- Character Types Section ----
        types_frame = ctk.CTkFrame(main_frame, fg_color="transparent")
        types_frame.pack(fill="x", pady=20)

        ctk.CTkLabel(
            types_frame,
            text="Character Types:",
            font=ctk.CTkFont(size=16, weight="bold")
        ).pack(anchor="w", padx=5, pady=(0, 10))

        # Checkboxes grid (2 columns for better layout)
        checkboxes_frame = ctk.CTkFrame(types_frame, fg_color="transparent")
        checkboxes_frame.pack(fill="x")

        # Row 1: Uppercase and Lowercase
        self.upper_check = ctk.CTkCheckBox(
            checkboxes_frame,
            text="Uppercase (A-Z)",
            variable=self.uppercase_var,
            font=ctk.CTkFont(size=13)
        )
        self.upper_check.grid(row=0, column=0, padx=10, pady=5, sticky="w")

        self.lower_check = ctk.CTkCheckBox(
            checkboxes_frame,
            text="Lowercase (a-z)",
            variable=self.lowercase_var,
            font=ctk.CTkFont(size=13)
        )
        self.lower_check.grid(row=0, column=1, padx=10, pady=5, sticky="w")

        # Row 2: Digits and Symbols
        self.digits_check = ctk.CTkCheckBox(
            checkboxes_frame,
            text="Digits (0-9)",
            variable=self.digits_var,
            font=ctk.CTkFont(size=13)
        )
        self.digits_check.grid(row=1, column=0, padx=10, pady=5, sticky="w")

        self.symbols_check = ctk.CTkCheckBox(
            checkboxes_frame,
            text="Symbols (!@#$%...)",
            variable=self.symbols_var,
            font=ctk.CTkFont(size=13)
        )
        self.symbols_check.grid(row=1, column=1, padx=10, pady=5, sticky="w")

        # Generate Button
        self.generate_btn = ctk.CTkButton(
            main_frame,
            text="✨ Generate Password",
            command=self.generate_password,
            font=ctk.CTkFont(size=15, weight="bold"),
            height=45,
            corner_radius=10
        )
        self.generate_btn.pack(pady=20, fill="x")

        # ---- Password Display Section ----
        password_frame = ctk.CTkFrame(main_frame, fg_color="transparent")
        password_frame.pack(fill="x", pady=10)

        ctk.CTkLabel(
            password_frame,
            text="Generated Password:",
            font=ctk.CTkFont(size=16, weight="bold")
        ).pack(anchor="w", padx=5)

        # Entry field for the generated password (read-only)
        self.password_entry = ctk.CTkEntry(
            password_frame,
            font=ctk.CTkFont(size=14),
            height=45,
            state="readonly",
            justify="center"
        )
        self.password_entry.pack(fill="x", pady=(5, 10))

        # Copy button and status label row
        copy_frame = ctk.CTkFrame(password_frame, fg_color="transparent")
        copy_frame.pack(fill="x")

        self.copy_btn = ctk.CTkButton(
            copy_frame,
            text="📋 Copy to Clipboard",
            command=self.copy_to_clipboard,
            font=ctk.CTkFont(size=13),
            height=35,
            fg_color="#2c3e50",
            hover_color="#1f2c38"
        )
        self.copy_btn.pack(side="left", padx=(0, 15))

        self.status_label = ctk.CTkLabel(
            copy_frame,
            text="",
            font=ctk.CTkFont(size=12),
            text_color="#2ecc71"
        )
        self.status_label.pack(side="left", fill="x", expand=True)

        # Optional: Add a hint label for symbols
        hint_label = ctk.CTkLabel(
            main_frame,
            text="💡 Tip: Use a length of at least 12 characters for strong security.",
            font=ctk.CTkFont(size=11, slant="italic"),
            text_color="#7f8c8d"
        )
        hint_label.pack(pady=(15, 5))

    def update_length_entry(self, value):
        """Update the length entry widget when slider moves."""
        self.length_entry.delete(0, "end")
        self.length_entry.insert(0, str(int(value)))

    def on_length_entry_return(self, event=None):
        """Handle manual length input from the entry field."""
        try:
            new_length = int(self.length_entry.get())
            # Clamp the value between 8 and 50
            new_length = max(8, min(50, new_length))
            self.length_var.set(new_length)
            self.length_slider.set(new_length)
            self.length_entry.delete(0, "end")
            self.length_entry.insert(0, str(new_length))
        except ValueError:
            # If invalid input, revert to current length value
            current = self.length_var.get()
            self.length_entry.delete(0, "end")
            self.length_entry.insert(0, str(current))

    def generate_password(self):
        """Generate a strong password based on user selections."""
        # Get user choices
        length = self.length_var.get()
        use_upper = self.uppercase_var.get()
        use_lower = self.lowercase_var.get()
        use_digits = self.digits_var.get()
        use_symbols = self.symbols_var.get()

        # Build list of character pools and their corresponding names
        pools = []
        pool_names = []
        if use_upper:
            pools.append(string.ascii_uppercase)
            pool_names.append("uppercase")
        if use_lower:
            pools.append(string.ascii_lowercase)
            pool_names.append("lowercase")
        if use_digits:
            pools.append(string.digits)
            pool_names.append("digits")
        if use_symbols:
            pools.append(string.punctuation)
            pool_names.append("symbols")

        # Validate: at least one character type must be selected
        if not pools:
            messagebox.showerror(
                "Error",
                "Please select at least one character type!\n(Try 'Uppercase', 'Lowercase', 'Digits', or 'Symbols')"
            )
            self.status_label.configure(text="❌ No character type selected!", text_color="#e74c3c")
            return

        # Ensure password length is sufficient to include one from each selected type
        if length < len(pools):
            messagebox.showerror(
                "Error",
                f"Password length ({length}) is too short.\n"
                f"With {len(pools)} selected type(s), minimum length is {len(pools)}.\n"
                f"Please increase the length or uncheck some options."
            )
            self.status_label.configure(text="❌ Length too short for selected types!", text_color="#e74c3c")
            return

        # Use cryptographically secure random generator
        rng = secrets.SystemRandom()

        # Step 1: Pick one random character from each selected pool (guarantees diversity)
        password_chars = []
        for pool in pools:
            password_chars.append(secrets.choice(pool))

        # Step 2: Fill the remaining characters from the combined pool (all selected types)
        remaining_len = length - len(pools)
        if remaining_len > 0:
            combined_pool = ''.join(pools)
            # Use choices for speed (with replacement)
            extra_chars = [rng.choice(combined_pool) for _ in range(remaining_len)]
            password_chars.extend(extra_chars)

        # Step 3: Shuffle the result to avoid predictable patterns
        rng.shuffle(password_chars)

        # Join into a single string
        generated_password = ''.join(password_chars)

        # Update the password entry field
        self.password_entry.configure(state="normal")
        self.password_entry.delete(0, "end")
        self.password_entry.insert(0, generated_password)
        self.password_entry.configure(state="readonly")

        # Update status message
        self.status_label.configure(text="✅ New password generated!", text_color="#2ecc71")
        # Clear status after 3 seconds
        self.after(3000, lambda: self.status_label.configure(text="") if self.status_label.cget("text") == "✅ New password generated!" else None)

    def copy_to_clipboard(self):
        """Copy the generated password to system clipboard."""
        password = self.password_entry.get()
        if not password:
            self.status_label.configure(text="❌ No password to copy!", text_color="#e74c3c")
            return

        self.clipboard_clear()
        self.clipboard_append(password)
        self.update()  # Keep clipboard data
        self.status_label.configure(text="📋 Password copied to clipboard!", text_color="#2ecc71")
        self.after(3000, lambda: self.status_label.configure(text="") if self.status_label.cget("text") == "📋 Password copied to clipboard!" else None)


if __name__ == "__main__":
    app = PasswordGeneratorApp()
    app.mainloop()
