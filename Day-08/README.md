# Python Calculator Suite

Two calculator applications demonstrating different interfaces in Python:
- **CLI Calculator** – Command‑line interactive calculator with basic arithmetic.
- **GUI Calculator** – Modern desktop calculator with a sleek Tkinter interface.

Both support addition, subtraction, multiplication, division, and modulo operations with proper error handling.

---

## Overview

| Calculator | Interface | Purpose |
|------------|-----------|---------|
| `CLI Calculator.py` | Command‑line | Lightweight, scriptable, works over SSH/terminal |
| `GUI Calculator.py` | Tkinter GUI | Visual, button‑driven, modern dark theme |

Both implement the same core operations: `+`, `-`, `*`, `/`, `%`.

---

## Features

### CLI Calculator
- Interactive loop – keep calculating until you type `quit`
- Accepts decimal numbers (floats)
- Handles division by zero and modulo by zero
- Automatically displays whole numbers as integers (e.g., `2.0` → `2`)
- Clear error messages for invalid operators or inputs

### GUI Calculator
- **Modern dark theme** – Purple accent, rounded buttons, hover effects
- Button layout similar to standard calculators
- Keyboard support? (not implemented by default – but easy to add)
- Displays results as integers when appropriate
- Error dialogs for division by zero or invalid expressions
- Clear button (`C`) resets the expression
- Result shown after `=` – next key press starts a new calculation

---

## Requirements

- **Python 3.6+** (both calculators)
- **Tkinter** – included with standard Python installations on Windows, macOS, and most Linux distributions.  
  *If missing on Linux, install via:*  
  `sudo apt-get install python3-tk` (Debian/Ubuntu)  
  `sudo dnf install python3-tkinter` (Fedora)

---

## Installation & Execution

No external dependencies – just Python.

### CLI Calculator

```bash
python "CLI Calculator.py"
```

Or make it executable (Unix):

```bash
chmod +x "CLI Calculator.py"
./CLI\ Calculator.py
```

### GUI Calculator

```bash
python "GUI Calculator.py"
```

> **Note:** The file names contain spaces. Use quotes or escape them in your terminal.

---

## Usage Guide

### CLI Mode

```
Simple CLI Calculator
Available operators: +, -, *, /, %
Type 'quit' to exit.

Enter operator (+, -, *, /, %) or 'quit': +
Enter first number: 15
Enter second number: 7
Result: 15.0 + 7.0 = 22

Enter operator (+, -, *, /, %) or 'quit': /
Enter first number: 10
Enter second number: 0
Error: Cannot divide by zero
```

- **Operators:** `+` `-` `*` `/` `%`
- **Special commands:** `quit` (case‑insensitive) to exit
- After each calculation, the prompt repeats.

### GUI Mode

<img width="499" height="784" alt="image" src="https://github.com/user-attachments/assets/447c000b-4b3e-4159-bfae-1fa7b9cdd220" />


1. **Click buttons** to build an expression (e.g., `5+3*2`)
2. Press `=` to evaluate
3. Press `C` to clear the display
4. After a result, typing a new digit starts a fresh expression

**Keyboard shortcuts:** (not built‑in, but you can add them easily – see “Extending”)

---

## Code Structure

### CLI Calculator

```python
def add(a, b): ...
def subtract(a, b): ...
# etc.

operations = {'+': add, '-': subtract, ...}

def main():   # input loop, validation, error handling
```

### GUI Calculator (Tkinter)

- `ModernCalculator` class
- `__init__`: sets up window, colors, fonts
- `create_widgets()`: builds the display and button grid
- `add_to_expression()`, `clear()`, `evaluate()`
- Hover effects using `<Enter>` / `<Leave>` bindings
- Uses `eval()` securely (restricted global/locals) – but **never expose this app to untrusted input**; it's a local desktop calculator.

---

## Error Handling

| Operation            | CLI Behaviour                                   | GUI Behaviour                                  |
|----------------------|-------------------------------------------------|------------------------------------------------|
| Division by zero     | Prints `Error: Cannot divide by zero`           | Popup dialog: “Cannot divide or modulo by zero”|
| Modulo by zero       | Same as above                                   | Same dialog                                    |
| Invalid expression   | (not possible – CLI validates operator first)   | Popup: “Invalid expression”                    |
| Non‑numeric input    | `Invalid number. Please enter numeric values.`  | (buttons only – prevents text input)           |

---

## Extending the Calculators

### Add a new operation (e.g., power `**`)

**CLI:**
```python
def power(a, b):
    return a ** b

operations['^'] = power
```
And update the operator list in the prompt.

**GUI:**
- Add a new button (e.g., `'^'`) in the `buttons` list with a lambda that calls `add_to_expression('**')`
- Update the `eval` call – it already handles `**`

### Add keyboard support to GUI

Bind keys to the root window:

```python
self.root.bind('<Key>', self.key_press)

def key_press(self, event):
    key = event.char
    if key in '0123456789+-*/.%':
        self.add_to_expression(key)
    elif key == '\r':  # Enter key
        self.evaluate()
    elif key == 'c' or key == 'C':
        self.clear()
```

### Persistent history

Both calculators can be extended to store past results using a list or file.

---

## License

These projects are created for educational purposes – demonstrating Python programming, arithmetic operations, user input validation, and GUI design with Tkinter.  
Free to use, modify, and distribute for learning.
