# Secure Password Generator (Modern GUI)

A professional password generation application built with **Python** and **CustomTkinter**. It creates cryptographically strong passwords based on user‑selected criteria (length, uppercase, lowercase, digits, symbols) and offers a modern dark‑themed interface with a slider, checkboxes, and one‑click copy to clipboard.

---

## Overview

**Secure Password Generator** helps users create strong, random passwords for online accounts. It uses Python’s `secrets` module (cryptographically secure random number generator) rather than `random`, making the passwords suitable for security‑sensitive applications.

The GUI is built with `customtkinter`, a modern library that extends Tkinter with stylish widgets, dark/light mode support, and responsive layouts.

---

## Features

| Feature                     | Description                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| **Custom length**           | Slide from 8 to 50 characters or type a number manually (clamped to 8–50).  |
| **Character type selection**| Uppercase (A‑Z), Lowercase (a‑z), Digits (0‑9), Symbols (!@#$%...).        |
| **At least one type required** – validation prevents generating empty or insecure passwords. |
| **Cryptographically secure** – uses `secrets.SystemRandom()` and `secrets.choice()`. |
| **Guaranteed diversity**    | At least one character from each selected type is included.                 |
| **Shuffling**               | Final password is shuffled to avoid predictable patterns.                   |
| **Copy to clipboard**       – one click copies the password, with a temporary status message. |
| **Dark mode by default**    – clean, eye‑friendly interface.                 |
| **Responsive layout**       – window resizes gracefully (minimum 500×580).   |
| **Input validation**        – prevents too short length relative to selected types. |

---

## Requirements

- **Python 3.7+**
- **CustomTkinter** library (not included in standard Python)

> CustomTkinter requires an internet connection for installation but runs offline afterwards.

---

## Installation

1. **Install CustomTkinter** using pip:

   ```bash
   pip install customtkinter
   ```

   *If you are on Linux, you may also need `python3-tk` (Tkinter) – but CustomTkinter usually pulls it automatically.*

2. **Download** the file `Password-Generator.py` to your computer.

3. (Optional) Verify installation:

   ```bash
   python -c "import customtkinter; print(customtkinter.__version__)"
   ```

---

## Running the App

Open a terminal/command prompt in the folder containing `Password-Generator.py` and run:

```bash
python Password-Generator.py
```

The application window will open, centered on your screen, with a default password already generated.

---

## Usage Guide

| Step | Action                                                                 |
|------|------------------------------------------------------------------------|
| 1    | **Adjust password length** – drag the slider or type a number (8–50). |
| 2    | **Select character types** – check/uncheck the boxes. At least one must be selected. |
| 3    | Click **✨ Generate Password** – a new password appears in the entry field. |
| 4    | Click **📋 Copy to Clipboard** – the password is copied to your system clipboard. |
| 5    | A temporary status message confirms the action.                        |

The password field is read‑only – you cannot edit it directly. Always regenerate if you change the options.

---

## How It Generates Passwords

The generation follows four steps to ensure both security and diversity:

1. **Build character pools** – based on the selected types (e.g., uppercase letters, digits, symbols).
2. **Pick one character from each selected pool** – guarantees that every chosen type appears at least once.
3. **Fill remaining length** – choose random characters from the *combined* pool of all selected types.
4. **Shuffle** – randomises the order so that the mandatory characters are not always at the beginning.

All randomness comes from `secrets.SystemRandom()` (or `secrets.choice()`), which uses OS‑provided entropy – suitable for password generation.

### Example

If you select:
- Length = 12
- Uppercase, Lowercase, Digits, Symbols (all four)

The algorithm:
- Creates 4 mandatory characters (one from each pool).
- Picks 8 additional characters from the combined pool (uppercase+lowercase+digits+symbols).
- Shuffles the 12 characters into a final password.

---

## Customization

You can modify the application by editing constants at the top of the file:

```python
ctk.set_appearance_mode("dark")      # Change to "light" or "system"
ctk.set_default_color_theme("blue")  # Try "green", "dark-blue"
```

- **Window size** – change `self.geometry("600x650")` in `__init__`.
- **Length limits** – modify `from_=8`, `to=50` in the slider and the clamping logic in `on_length_entry_return`.
- **Default selections** – change the `BooleanVar` initial values (e.g., set `value=False` for symbols).

---

## Extending the Application

The code is modular and easy to enhance. Here are some ideas:

- **Add password strength meter** – estimate strength based on length and character types.
- **Save passwords** – store generated passwords in an encrypted local file (with a master password).
- **Password history** – keep a list of recently generated passwords.
- **Export to text file** – add a button to save the current password to a `.txt` file.
- **QR code generation** – display password as a QR code for easy phone transfer.
- **Pronounceable passwords** – add an option to generate “memorable” passwords (e.g., random words + digits).
- **Light/dark mode toggle** – the app currently starts in dark mode, but you could add a switch to change it at runtime.

---

## License

This project is created for **educational purposes** – to demonstrate GUI development with CustomTkinter, cryptographically secure random generation, and event‑driven programming. You are free to use, modify, and distribute it for learning.
