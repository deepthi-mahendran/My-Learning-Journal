import random
import customtkinter as ctk

# Set appearance and color theme
ctk.set_appearance_mode("System")  # "Dark", "Light", "System"
ctk.set_default_color_theme("green")


class RockPaperScissorsApp:
    def __init__(self):
        self.window = ctk.CTk()
        self.window.title("Rock-Paper-Scissors")
        self.window.geometry("500x450")
        self.window.resizable(False, False)

        # Score dictionary
        self.scores = {"Player": 0, "Computer": 0}

        # Available moves
        self.moves = ["Rock", "Paper", "Scissors"]

        # ---------- UI Elements ----------
        # Title
        self.title_label = ctk.CTkLabel(
            self.window, text="Rock ⚔ Paper 📄 Scissors ✂️",
            font=ctk.CTkFont(size=24, weight="bold")
        )
        self.title_label.pack(pady=10)

        # Score Frame
        self.score_frame = ctk.CTkFrame(self.window)
        self.score_frame.pack(pady=10, padx=20, fill="x")

        self.player_score_label = ctk.CTkLabel(
            self.score_frame, text=f"Player: {self.scores['Player']}",
            font=ctk.CTkFont(size=18)
        )
        self.player_score_label.grid(row=0, column=0, padx=30, pady=5)

        self.computer_score_label = ctk.CTkLabel(
            self.score_frame, text=f"Computer: {self.scores['Computer']}",
            font=ctk.CTkFont(size=18)
        )
        self.computer_score_label.grid(row=0, column=1, padx=30, pady=5)

        # Computer choice display
        self.computer_choice_label = ctk.CTkLabel(
            self.window, text="Computer's choice: —",
            font=ctk.CTkFont(size=16)
        )
        self.computer_choice_label.pack(pady=(20, 5))

        # Result display
        self.result_label = ctk.CTkLabel(
            self.window, text="Click a button to start!",
            font=ctk.CTkFont(size=16, weight="bold"),
            text_color="white"
        )
        self.result_label.pack(pady=5)

        # Buttons Frame
        self.buttons_frame = ctk.CTkFrame(self.window)
        self.buttons_frame.pack(pady=20)

        self.rock_btn = ctk.CTkButton(
            self.buttons_frame, text="Rock 🪨", width=120,
            command=lambda: self.play("Rock")
        )
        self.rock_btn.grid(row=0, column=0, padx=10, pady=5)

        self.paper_btn = ctk.CTkButton(
            self.buttons_frame, text="Paper 📄", width=120,
            command=lambda: self.play("Paper")
        )
        self.paper_btn.grid(row=0, column=1, padx=10, pady=5)

        self.scissors_btn = ctk.CTkButton(
            self.buttons_frame, text="Scissors ✂️", width=120,
            command=lambda: self.play("Scissors")
        )
        self.scissors_btn.grid(row=0, column=2, padx=10, pady=5)

        # Reset button
        self.reset_btn = ctk.CTkButton(
            self.window, text="Reset Score", width=150,
            command=self.reset_scores, fg_color="darkred", hover_color="red"
        )
        self.reset_btn.pack(pady=10)

        # Run the application
        self.window.mainloop()

    def determine_winner(self, player_move, computer_move):
        """Return 'player', 'computer', or 'tie' based on the moves."""
        if player_move == computer_move:
            return "tie"

        # Winning conditions: player beats computer?
        win_conditions = {
            "Rock": "Scissors",
            "Paper": "Rock",
            "Scissors": "Paper"
        }

        if win_conditions[player_move] == computer_move:
            return "player"
        else:
            return "computer"

    def update_scores_and_ui(self, winner):
        """Update the score dictionary and refresh labels."""
        if winner == "player":
            self.scores["Player"] += 1
        elif winner == "computer":
            self.scores["Computer"] += 1

        # Refresh score labels
        self.player_score_label.configure(text=f"Player: {self.scores['Player']}")
        self.computer_score_label.configure(text=f"Computer: {self.scores['Computer']}")

    def play(self, player_move):
        """Main game logic for one round."""
        # Computer randomly selects a move
        computer_move = random.choice(self.moves)
        self.computer_choice_label.configure(text=f"Computer's choice: {computer_move}")

        # Determine winner
        winner = self.determine_winner(player_move, computer_move)

        # Build result message
        if winner == "tie":
            result_text = f"It's a tie! Both chose {player_move}."
            self.result_label.configure(text=result_text, text_color="gray")
        elif winner == "player":
            result_text = f"You win! {player_move} beats {computer_move}."
            self.result_label.configure(text=result_text, text_color="lightgreen")
            self.update_scores_and_ui("player")
        else:
            result_text = f"You lose! {computer_move} beats {player_move}."
            self.result_label.configure(text=result_text, text_color="lightcoral")
            self.update_scores_and_ui("computer")

    def reset_scores(self):
        """Reset both scores to zero."""
        self.scores["Player"] = 0
        self.scores["Computer"] = 0
        self.player_score_label.configure(text=f"Player: {self.scores['Player']}")
        self.computer_score_label.configure(text=f"Computer: {self.scores['Computer']}")
        self.result_label.configure(text="Scores reset! Play again.", text_color="white")
        self.computer_choice_label.configure(text="Computer's choice: —")


if __name__ == "__main__":
    app = RockPaperScissorsApp()
