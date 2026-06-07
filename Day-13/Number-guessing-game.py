import tkinter as tk
from tkinter import messagebox
import random

class NumberGuessingGame:
    def __init__(self, master):
        self.master = master
        master.title("🎯 Number Guessing Game")
        master.resizable(False, False)
        master.geometry("400x300")

        # Game state variables (initialised later)
        self.secret_number = None
        self.guess_count = 0
        self.lower_bound = 1
        self.upper_bound = 100

        # --- GUI elements (created first) ---
        title_label = tk.Label(master, text="Guess the Number!", font=("Arial", 16, "bold"))
        title_label.pack(pady=10)

        range_label = tk.Label(master, text=f"Range: {self.lower_bound} – {self.upper_bound}")
        range_label.pack()

        self.hint_label = tk.Label(master, text="Enter your guess below:", fg="blue")
        self.hint_label.pack(pady=5)

        self.entry = tk.Entry(master, width=10, font=("Arial", 14))
        self.entry.pack()
        self.entry.focus()

        self.guess_button = tk.Button(master, text="Guess", command=self.check_guess, bg="lightgreen", width=12)
        self.guess_button.pack(pady=5)

        self.new_game_button = tk.Button(master, text="New Game", command=self.new_game, bg="lightblue", width=12)
        self.new_game_button.pack(pady=5)

        self.info_label = tk.Label(master, text="")          # now created before new_game()
        self.info_label.pack(pady=5)

        # Bind Enter key
        master.bind('<Return>', lambda event: self.check_guess())

        # --- Initialise game state (after all widgets exist) ---
        self.new_game()

    def new_game(self):
        """Reset the game: new random number, reset guess count, clear display."""
        self.secret_number = random.randint(self.lower_bound, self.upper_bound)
        self.guess_count = 0
        self.info_label.config(text="")
        self.hint_label.config(text="Enter your guess below:", fg="blue")
        self.entry.delete(0, tk.END)
        self.entry.config(state="normal")
        self.guess_button.config(state="normal")
        self.entry.focus()
        # Optional debug: print(self.secret_number)

    def check_guess(self):
        """Validate user input, compare with secret number, display hint."""
        guess_text = self.entry.get().strip()

        if not guess_text:
            messagebox.showwarning("Invalid input", "Please enter a number.")
            self.entry.delete(0, tk.END)
            return

        try:
            guess = int(guess_text)
        except ValueError:
            messagebox.showwarning("Invalid input", f"'{guess_text}' is not an integer.\nEnter a whole number between {self.lower_bound} and {self.upper_bound}.")
            self.entry.delete(0, tk.END)
            return

        if not (self.lower_bound <= guess <= self.upper_bound):
            messagebox.showwarning("Out of range", f"Please enter a number between {self.lower_bound} and {self.upper_bound}.")
            self.entry.delete(0, tk.END)
            return

        self.guess_count += 1
        self.info_label.config(text=f"Attempts: {self.guess_count}")

        if guess < self.secret_number:
            self.hint_label.config(text="📉 Too low! Try a higher number.", fg="orange")
        elif guess > self.secret_number:
            self.hint_label.config(text="📈 Too high! Try a lower number.", fg="orange")
        else:
            self.hint_label.config(text=f"🎉 CORRECT! The number was {self.secret_number}.", fg="green")
            self.info_label.config(text=f"You guessed it in {self.guess_count} attempt(s).")
            self.entry.config(state="disabled")
            self.guess_button.config(state="disabled")
            messagebox.showinfo("Congratulations!", f"You guessed the number {self.secret_number} in {self.guess_count} tries!")
            return

        self.entry.delete(0, tk.END)
        self.entry.focus()

if __name__ == "__main__":
    root = tk.Tk()
    game = NumberGuessingGame(root)
    root.mainloop()
