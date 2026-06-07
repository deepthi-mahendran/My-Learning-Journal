# Number Guessing Game (GUI)

A classic number guessing game with a graphical user interface built using Python's Tkinter. The program selects a random number within a range, and the player tries to guess it with hints after each attempt.

---

## Overview

**Number Guessing Game** is a simple but addictive desktop game where the computer picks a secret number between 1 and 100 (inclusive). The player enters guesses, and the game responds with:

- **Too low** – guess a higher number
- **Too high** – guess a lower number
- **Correct!** – you win, and the game shows the number of attempts

The interface is clean, with a hint label, attempt counter, and buttons for guessing and starting a new game.

---

## Features

- **Random secret number** – changes every new game.
- **Input validation** – rejects non‑integers, empty entries, and numbers outside 1–100.
- **Hints after each guess** – visual feedback (📉 too low / 📈 too high / 🎉 correct).
- **Attempt counter** – shows how many guesses you have made.
- **Keyboard support** – press `Enter` to submit a guess.
- **New game button** – resets the game with a new secret number.
- **Disable after win** – entry field and guess button become inactive until you start a new game.
- **Informative dialogs** – warning for invalid input and congratulatory message on winning.

---

## Requirements

- **Python 3.6+** (Tkinter is included in standard Python distributions)
- No external packages – uses only `tkinter` and `random` from the standard library.

> **Note:** On some Linux distributions, you may need to install `python3-tk` separately:
> ```bash
> sudo apt-get install python3-tk
> ```

---

## Installation & Execution

1. **Save the file** as `Number-guessing-game.py`.

2. **Run the script** from a terminal or command prompt:

   ```bash
   python Number-guessing-game.py
   ```

   Or double‑click the file if Python is associated with `.py` files.

No additional setup is required.

---

## How to Play

| Step | Action                                                                 |
|------|------------------------------------------------------------------------|
| 1    | The game secretly picks a number between **1 and 100**.                |
| 2    | Type your guess into the entry box.                                    |
| 3    | Click the **Guess** button or press **Enter**.                         |
| 4    | Read the hint: *Too low* / *Too high* / *Correct!*.                    |
| 5    | Keep guessing until you find the number.                               |
| 6    | After winning, click **New Game** to play again with a new secret number. |

- The **attempt counter** increments after each valid guess.
- If you enter text that is not a number, or a number outside 1–100, a warning dialog appears and the guess is not counted.

---

## Game Rules

- The secret number is a **random integer** between `1` and `100` (inclusive).
- Each guess must be an integer within the same range.
- There is no limit on the number of guesses.
- The game ends when you guess correctly – you are then prompted to start a new game.

---

## Code Structure

| Component              | Description                                                                 |
|------------------------|-----------------------------------------------------------------------------|
| `NumberGuessingGame` class | Encapsulates the game logic and GUI.                                      |
| `__init__`             | Sets up the window, widgets, and initialises the game state.                |
| `new_game()`           | Resets `secret_number`, `guess_count`, re‑enables widgets, and clears texts.|
| `check_guess()`        | Validates input, compares with secret number, updates hints and attempts.   |
| `main` block           | Creates the Tkinter root window and starts the event loop.                  |

The game uses standard Tkinter widgets: `Label`, `Entry`, `Button`, and `messagebox`.

---

## Extending the Game

The code is simple and easy to modify. Here are some ideas:

- **Change the range** – modify `self.lower_bound` and `self.upper_bound` in `__init__`.
- **Add difficulty levels** – let the user choose Easy (1–50), Medium (1–100), Hard (1–500).
- **Limit the number of guesses** – add a maximum attempt counter and end the game if exceeded.
- **Keep high score** – store the lowest number of attempts in a file and display it.
- **Add sound effects** – use `playsound` or `winsound` for correct/wrong guesses.
- **Visual feedback with colours** – change the hint label colour dynamically (already does: blue → orange → green).

Example – changing the range to 1–500:

```python
self.lower_bound = 1
self.upper_bound = 500
```

And update the range label accordingly.

---

## License

This project is created for **educational purposes** – to demonstrate GUI programming with Tkinter, event handling, random number generation, and input validation. You are free to use, modify, and distribute it for learning.
