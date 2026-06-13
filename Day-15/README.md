# Rock Paper Scissors Game (GUI)

A modern, visually appealing Rock‑Paper‑Scissors game built with Python and **CustomTkinter**. Play against the computer, track your score, and reset anytime – all in a clean, cross‑platform desktop interface.

---

## Overview

**Rock Paper Scissors** is a classic two‑player hand game, here implemented as a single‑player versus the computer. The player chooses one of three moves (Rock, Paper, or Scissors) by clicking a button. The computer randomly selects its move, and the winner is determined according to the traditional rules. Scores are updated and displayed in real time.

The application uses `customtkinter` for a modern, responsive GUI with a system‑aware appearance (light/dark mode follows your OS settings by default) and a green accent theme.

---

## Features

- **Three move buttons** – Rock 🪨, Paper 📄, Scissors ✂️.
- **Computer random choice** – uses `random.choice()`.
- **Real‑time score display** – Player vs Computer.
- **Winner announcement** – colour‑coded results (green = win, red = loss, gray = tie).
- **Reset Score button** – sets both scores back to zero.
- **Computer move shown** – always visible after each round.
- **Clean, non‑resizable window** – fixed size 500×450 pixels.
- **Appearance follows system** – dark/light mode automatically matches your OS (can be overridden).

---

## Requirements

- **Python 3.7+**
- **CustomTkinter** library (not part of the standard library)

> CustomTkinter requires an internet connection for installation but works offline afterwards.

---

## Installation

1. **Install CustomTkinter** using pip:

   ```bash
   pip install customtkinter
   ```

   *On some Linux distributions, you might need to install `python3-tk` separately before CustomTkinter:*

   ```bash
   sudo apt-get install python3-tk
   ```

2. **Download** the file `Rock-Paper-Scissor.py` to your computer.

3. (Optional) Verify CustomTkinter installation:

   ```bash
   python -c "import customtkinter; print(customtkinter.__version__)"
   ```

---

## Running the Game

Open a terminal/command prompt in the folder containing `Rock-Paper-Scissor.py` and run:

```bash
python Rock-Paper-Scissor.py
```

The game window will appear. No command‑line arguments are needed.

---

## How to Play

1. **Click one of the three buttons**: Rock 🪨, Paper 📄, or Scissors ✂️.
2. The computer randomly picks its move – shown below the score panel.
3. The result is displayed with a coloured message:
   - **Green** – you win
   - **Red** – computer wins
   - **Gray** – tie
4. The scores update automatically.
5. Click **Reset Score** to zero out both scores (does not affect the current round).

Play as many rounds as you like. The game never ends – you can keep playing until you close the window.

---

## Game Rules

The traditional rules apply:

- **Rock crushes Scissors** → Rock wins
- **Scissors cuts Paper** → Scissors wins
- **Paper covers Rock** → Paper wins
- Same move → Tie (no score change)

The computer’s choice is completely random, with equal probability (1/3 each).

---

## Code Structure

| Component                     | Description                                                                 |
|-------------------------------|-----------------------------------------------------------------------------|
| `RockPaperScissorsApp` class  | Encapsulates the game logic and GUI.                                        |
| `__init__`                    | Creates the window, score dictionary, UI widgets, and starts the main loop. |
| `determine_winner()`          | Returns `'player'`, `'computer'`, or `'tie'` based on the two moves.       |
| `update_scores_and_ui()`      | Adds a point to the winner and refreshes the score labels.                  |
| `play(player_move)`           | Main game flow: computer choice, winner determination, UI updates.          |
| `reset_scores()`              | Resets both scores to zero and clears the result/computer choice display.   |
| `if __name__ == "__main__"`   | Creates an instance of the app.                                             |

The UI uses `customtkinter` widgets: `CTk`, `CTkLabel`, `CTkFrame`, `CTkButton`. The layout is managed with `pack()` and `grid()`.

---

## Customization

You can easily change the look and feel by modifying the top lines:

```python
ctk.set_appearance_mode("System")   # Options: "Dark", "Light", "System"
ctk.set_default_color_theme("green") # Options: "blue", "dark-blue", "purple"
```

- **Window size** – change `self.window.geometry("500x450")` in `__init__`.
- **Button text / emojis** – edit the button labels inside the `CTkButton` constructors.
- **Score display font** – adjust the `font=ctk.CTkFont(size=18)` parameter.

Example – switch to dark mode always:

```python
ctk.set_appearance_mode("Dark")
```

---

## Extending the Game

The code is simple and well‑structured, making it easy to add new features:

- **Best‑of‑series mode** – track rounds and announce a winner after 3, 5, or 7 rounds.
- **Animation or countdown** – show a short delay before revealing the computer’s move.
- **Sound effects** – use `playsound` or `winsound` to play a click or win/lose sound.
- **Statistics** – track total rounds played, win percentage, longest win streak.
- **Custom moves** – add Lizard and Spock (Big Bang Theory style) – would require extending rules.
- **Network multiplayer** – two players on different computers (advanced).

## License

This project is created for **educational purposes** – to demonstrate GUI programming with CustomTkinter, random choices, event handling, and basic game logic. You are free to use, modify, and distribute it for learning.
