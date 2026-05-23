# Java Quiz Game

A simple interactive command-line quiz game built in Java. Test your knowledge with five general knowledge questions covering geography, science, art, and chemistry.

## Features

- **5 multiple-choice questions** with 4 options each (A, B, C, D)
- **Input validation** – accepts only A, B, C, or D
- **Instant feedback** – shows whether your answer was correct or incorrect
- **Score tracking** – counts correct answers and calculates final percentage
- **Performance message** – displays encouraging feedback based on your score
- **Answer summary** – reviews all your answers with correct results at the end
- **Clean console output** – formatted with separators for easy reading

## Prerequisites

- **Java Development Kit (JDK)** 8 or higher  
  Check if Java is installed:
  ```bash
  java -version
  ```

## How to Run

1. **Save the file**  
   Make sure the file is saved as `QuizGame.java` (the class name must match the filename).

2. **Compile the program**  
   Open a terminal/command prompt in the folder containing the file and run:
   ```bash
   javac QuizGame.java
   ```

3. **Run the game**  
   ```bash
   java QuizGame
   ```

## Example Gameplay

```
============================================================
          WELCOME TO THE JAVA QUIZ GAME
============================================================
Answer each question by typing the letter (A, B, C, or D).

1. What is the capital of France?
   A. London
   B. Paris
   C. Berlin
   D. Madrid

Your answer: B
✓ Correct!
...
============================================================
                 QUIZ RESULTS
============================================================
Total Questions: 5
Correct Answers: 4
Incorrect Answers: 1
Final Score: 80.0%
Performance: Good job! Well done!
```

## Code Structure

| Component | Description |
|-----------|-------------|
| `correctAnswers[]` | Stores correct answer keys (char array) |
| `userAnswers[]` | Stores player’s answers for summary |
| `questions[][]` | 2D array with questions and options |
| Input validation | `while` loop + `if` ensures valid A–D input |
| Answer checking | `switch` statement compares user answer to correct answer |
| Score calculation | Cast to `double` for percentage precision |
| Performance feedback | `if-else if-else` ladder based on score |
| Answer summary | Ternary operator (`? :`) for checkmark/cross display |

## Possible Enhancements

- Add more questions
- Implement a scoring system with points per question
- Add a replay option without restarting the program
- Load questions from an external file
- Track high scores

## License

This project is free to use for learning and personal purposes.

---

**Enjoy the quiz!**
