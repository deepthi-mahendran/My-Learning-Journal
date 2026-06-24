# Text Analysis Tool

A comprehensive Java console application that performs various text analysis operations on user-provided input. This program demonstrates practical applications of Java String manipulation, collection frameworks (HashMap, HashSet), and input validation techniques.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Compilation and Execution](#compilation-and-execution)
- [Usage Guide](#usage-guide)
- [Key Concepts Demonstrated](#key-concepts-demonstrated)
- [Code Structure](#code-structure)
- [Input Validation](#input-validation)
- [Sample Output](#sample-output)
- [Extending the Program](#extending-the-program)

---

## Overview

**Text Analysis Tool** is a console-based Java application that allows users to analyze text input in multiple ways. It provides:

- Character and word counts
- Frequency analysis for specific characters and words
- Most common character and word identification
- Unique word counting
- Case-insensitive analysis with punctuation removal

The program is designed with a clean, modular structure that separates input collection, validation, and analysis logic. It serves as an excellent example of practical Java programming for educational purposes.

---

## Features

| Feature                     | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| **Character Count**         | Total characters (including and excluding spaces).                                               |
| **Word Count**              | Number of words in the text.                                                                     |
| **Most Common Character**   | Identifies the most frequently occurring character (case‑insensitive, excluding spaces).         |
| **Character Frequency**     | User‑specified character frequency count (case‑insensitive).                                    |
| **Word Frequency**          | User‑specified word frequency count (case‑insensitive, punctuation‑removed).                    |
| **Unique Word Count**       | Number of unique words in the text (case‑insensitive, punctuation‑removed).                     |
| **Most Common Word**        | Identifies the most frequently occurring word (case‑insensitive, punctuation‑removed).           |
| **Input Validation**        | Ensures non‑empty text, minimum length warnings, and valid character/word inputs.                |
| **Case-Insensitive Analysis** | All comparisons are case‑insensitive for more accurate results.                                |
| **Punctuation Removal**     | Words are cleaned of punctuation for better frequency analysis.                                  |
| **User-Friendly Prompts**   | Clear instructions and error messages guide the user through each step.                         |

---

## Technology Stack

- **Java SE** — Standard Edition (no external libraries)
- **Java Collections Framework** — `HashMap`, `HashSet`, `Map.Entry`
- **Java String API** — `split()`, `toCharArray()`, `replaceAll()`, `toLowerCase()`
- **Scanner** — For console input

**No external dependencies** — pure Java standard library.

---

## Requirements

- **Java** — JDK 8 or higher (uses `String.repeat()` which was introduced in Java 11, so JDK 11+ is recommended)

> **Note:** The code uses `String.repeat()` which is available from Java 11 onward. If using Java 8, replace `"=".repeat(60)` with a loop or use `String.join("", Collections.nCopies(60, "="))`.

---

## Compilation and Execution

### Compile

```bash
javac TextAnalysisTool.java
```

### Run

```bash
java TextAnalysisTool
```

No command‑line arguments are required.

---

## Usage Guide

### Step 1: Launch the Program

```
============================================================
           TEXT ANALYSIS TOOL
============================================================
This tool performs various operations on your text input.
Please follow the prompts to analyze your text.
```

### Step 2: Enter Text

```
Please enter a paragraph or lengthy text: 
```

- The input must not be empty.
- If the text is less than 10 characters, a warning is shown but the input is accepted.

### Step 3: Review Analysis Results

The program displays:

1. Total characters (including spaces)
2. Characters excluding spaces
3. Total words
4. Most common character
5. Frequency of a user‑specified character
6. Frequency of a user‑specified word
7. Number of unique words
8. Most common word

### Step 4: Enter Character for Frequency Analysis

```
Enter a character to check its frequency: 
```

- Must be exactly **one** character.

### Step 5: Enter Word for Frequency Analysis

```
Enter a word to check its frequency: 
```

- Must contain **only letters** (A-Z or a-z).

### Step 6: Exit

After analysis, the program displays a farewell message and exits.

---

## Key Concepts Demonstrated

| Concept                     | Implementation                                                                 |
|-----------------------------|--------------------------------------------------------------------------------|
| **String Manipulation**     | `split()`, `toCharArray()`, `replaceAll()`, `toLowerCase()`                    |
| **Collections Framework**   | `HashMap` for frequency maps, `HashSet` for unique word counting               |
| **Iteration**               | Enhanced for‑loops, `Map.Entry` iteration                                      |
| **Input Validation**        | `while` loops with validation conditions, `isEmpty()`, `length()`, regex       |
| **Method Decomposition**    | Each analysis task is a separate, well‑named method                           |
| **Case‑Insensitive Analysis** | `toLowerCase()` conversion for consistent comparisons                        |
| **Punctuation Removal**     | `replaceAll("[^a-zA-Z]", "")` for cleaning words                               |

---

## Code Structure

```
TextAnalysisTool.java
├── main()
│   ├── displayHeader()
│   ├── getValidatedTextInput()
│   └── performTextAnalysis()
│       ├── getCharacterCount()
│       ├── getCharacterCountExcludingSpaces()
│       ├── getWordCount()
│       ├── getMostCommonCharacter()
│       ├── getCharacterFrequency()
│       ├── getValidatedCharacterInput()
│       ├── getWordFrequency()
│       ├── getValidatedWordInput()
│       ├── getUniqueWordCount()
│       └── getMostCommonWord()
└── (end)
```

### Method Breakdown

| Method                          | Purpose                                                                 |
|---------------------------------|-------------------------------------------------------------------------|
| `displayHeader()`               | Prints the program title and introductory message.                      |
| `getValidatedTextInput()`       | Prompts for and validates text input.                                   |
| `performTextAnalysis()`         | Orchestrates all analysis operations and displays results.              |
| `getCharacterCount()`           | Returns total characters (including spaces).                            |
| `getCharacterCountExcludingSpaces()` | Returns characters excluding spaces.                                  |
| `getWordCount()`                | Returns number of words (split by spaces).                              |
| `getMostCommonCharacter()`      | Finds the most common character (case‑insensitive, spaces excluded).    |
| `getCharacterFrequency()`       | Counts occurrences of a specific character.                             |
| `getWordFrequency()`            | Counts occurrences of a specific word.                                  |
| `getUniqueWordCount()`          | Returns number of unique words.                                         |
| `getMostCommonWord()`           | Finds the most common word.                                             |
| `getValidatedCharacterInput()`  | Prompts for and validates a single character.                           |
| `getValidatedWordInput()`       | Prompts for and validates a word (letters only).                        |

---

## Input Validation

The program validates all user inputs to ensure robustness:

| Input                 | Validation Rules                                                                 |
|-----------------------|----------------------------------------------------------------------------------|
| **Text**              | Cannot be empty. Warning if less than 10 characters.                           |
| **Character**         | Must be exactly **one** character.                                              |
| **Word**              | Must contain **only letters** (A-Z, a-z).                                       |

All validation is handled with `while` loops that repeatedly prompt the user until valid input is provided.

---

## Sample Output

```
============================================================
           TEXT ANALYSIS TOOL
============================================================
This tool performs various operations on your text input.
Please follow the prompts to analyze your text.

Please enter a paragraph or lengthy text: The quick brown fox jumps over the lazy dog.

Text successfully recorded. Analyzing...

------------------------------------------------------------
ANALYSIS RESULTS
------------------------------------------------------------
1. Total Characters (including spaces): 44
   Characters (excluding spaces): 35
2. Total Words: 9
3. Most Common Character: 'e' (appears 3 times)

Enter a character to check its frequency: o
4. Frequency of 'o': 4 occurrence(s)

Enter a word to check its frequency: the
5. Frequency of 'the': 2 occurrence(s)
6. Number of Unique Words: 8
7. Most Common Word: 'the'

------------------------------------------------------------

Thank you for using the Text Analysis Tool!
```

---

## Extending the Program

The modular design makes it easy to add new features:

### Add Sentence Count

```java
private static int getSentenceCount(String text) {
    if (text == null || text.isEmpty()) return 0;
    return text.split("[.!?]+").length;
}
```

### Add Average Word Length

```java
private static double getAverageWordLength(String text) {
    String[] words = text.split("\\s+");
    int totalChars = 0;
    for (String word : words) {
        totalChars += word.replaceAll("[^a-zA-Z]", "").length();
    }
    return (double) totalChars / words.length;
}
```

### Add Palindrome Detection

```java
private static boolean isPalindrome(String word) {
    String clean = word.toLowerCase().replaceAll("[^a-z]", "");
    return clean.equals(new StringBuilder(clean).reverse().toString());
}
```

### Add Vowel/Consonant Count

```java
private static int countVowels(String text) {
    int count = 0;
    for (char c : text.toLowerCase().toCharArray()) {
        if ("aeiou".indexOf(c) != -1) count++;
    }
    return count;
}
```

### Save Results to File

```java
private static void saveResultsToFile(String text, String results) {
    try (PrintWriter writer = new PrintWriter("analysis_results.txt")) {
        writer.println("Original Text:\n" + text);
        writer.println("\nAnalysis Results:\n" + results);
        System.out.println("Results saved to analysis_results.txt");
    } catch (IOException e) {
        System.err.println("Error saving results: " + e.getMessage());
    }
}
```

---

## Best Practices Demonstrated

- **Method Decomposition** — Each task is in its own method for readability and reusability.
- **Input Validation** — All user inputs are validated before processing.
- **Resource Management** — Scanner is properly closed to prevent resource leaks.
- **Case-Insensitive Analysis** — Uses `toLowerCase()` for consistent comparisons.
- **Punctuation Handling** — Removes punctuation for accurate word analysis.
- **User Feedback** — Clear error messages and warnings guide the user.
