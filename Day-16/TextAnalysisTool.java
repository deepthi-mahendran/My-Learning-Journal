import java.util.*;
import java.util.Map.Entry;

/**
 * TextAnalysisTool - A comprehensive text analysis program that performs
 * various operations on user-provided text input.
 * 
 * This program demonstrates practical application of Java String manipulation,
 * collection frameworks, and input validation techniques.
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class TextAnalysisTool {
    
    /**
     * The main method serves as the entry point for the program.
     * It orchestrates the entire text analysis workflow.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Display program header
        displayHeader();
        
        // Step 1: Collect user input with validation
        String text = getValidatedTextInput(scanner);
        
        // Step 2: Perform and display analysis
        performTextAnalysis(text, scanner);
        
        // Close scanner to prevent resource leak
        scanner.close();
        System.out.println("\nThank you for using the Text Analysis Tool!");
    }
    
    /**
     * Displays the program header with title and separator lines.
     */
    private static void displayHeader() {
        System.out.println("=" .repeat(60));
        System.out.println("           TEXT ANALYSIS TOOL");
        System.out.println("=" .repeat(60));
        System.out.println("This tool performs various operations on your text input.");
        System.out.println("Please follow the prompts to analyze your text.\n");
    }
    
    /**
     * Prompts the user for text input and validates that it is not empty.
     * 
     * @param scanner Scanner object for user input
     * @return Validated text input from the user
     */
    private static String getValidatedTextInput(Scanner scanner) {
        String text = "";
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print("Please enter a paragraph or lengthy text: ");
            text = scanner.nextLine().trim();
            
            if (text.isEmpty()) {
                System.out.println("Error: Input cannot be empty. Please enter some text.\n");
            } else if (text.length() < 10) {
                System.out.println("Warning: Text is very short. For better analysis, ");
                System.out.println("please enter at least 10 characters.\n");
                // Allow the input but display a warning
                validInput = true;
            } else {
                validInput = true;
            }
        }
        
        System.out.println("\nText successfully recorded. Analyzing...\n");
        return text;
    }
    
    /**
     * Performs all text analysis operations and displays results.
     * 
     * @param text The text to analyze
     * @param scanner Scanner object for user interaction
     */
    private static void performTextAnalysis(String text, Scanner scanner) {
        // Section separator
        System.out.println("-" .repeat(60));
        System.out.println("ANALYSIS RESULTS");
        System.out.println("-" .repeat(60));
        
        // 1. Character Count
        int charCount = getCharacterCount(text);
        System.out.printf("1. Total Characters (including spaces): %d%n", charCount);
        System.out.printf("   Characters (excluding spaces): %d%n", getCharacterCountExcludingSpaces(text));
        
        // 2. Word Count
        int wordCount = getWordCount(text);
        System.out.printf("2. Total Words: %d%n", wordCount);
        
        // 3. Most Common Character
        char mostCommonChar = getMostCommonCharacter(text);
        int mostCommonCharCount = getCharacterFrequency(text, mostCommonChar);
        System.out.printf("3. Most Common Character: '%c' (appears %d times)%n", 
                          mostCommonChar, mostCommonCharCount);
        
        // 4. Character Frequency (user-specified)
        char targetChar = getValidatedCharacterInput(scanner);
        int charFreq = getCharacterFrequency(text, targetChar);
        System.out.printf("4. Frequency of '%c': %d occurrence(s)%n", targetChar, charFreq);
        
        // 5. Word Frequency (user-specified)
        String targetWord = getValidatedWordInput(scanner);
        int wordFreq = getWordFrequency(text, targetWord);
        System.out.printf("5. Frequency of '%s': %d occurrence(s)%n", targetWord, wordFreq);
        
        // 6. Unique Words
        int uniqueWordCount = getUniqueWordCount(text);
        System.out.printf("6. Number of Unique Words: %d%n", uniqueWordCount);
        
        // 7. Additional Insight: Most Common Word
        String mostCommonWord = getMostCommonWord(text);
        System.out.printf("7. Most Common Word: '%s'%n", mostCommonWord);
        
        System.out.println("-" .repeat(60));
    }
    
    /**
     * Calculates the total number of characters in the text.
     * 
     * @param text The input text
     * @return Total character count
     */
    private static int getCharacterCount(String text) {
        return text.length();
    }
    
    /**
     * Calculates the number of characters excluding spaces.
     * 
     * @param text The input text
     * @return Character count excluding spaces
     */
    private static int getCharacterCountExcludingSpaces(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c != ' ') {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Calculates the total number of words in the text.
     * Words are assumed to be separated by spaces.
     * 
     * @param text The input text
     * @return Total word count
     */
    private static int getWordCount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }
    
    /**
     * Finds the most common character in the text (case-insensitive).
     * In case of a tie, the first encountered character is returned.
     * 
     * @param text The input text
     * @return The most common character
     */
    private static char getMostCommonCharacter(String text) {
        if (text == null || text.isEmpty()) {
            return ' ';
        }
        
        // Convert to lowercase for case-insensitive analysis
        String lowerText = text.toLowerCase();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        
        for (char c : lowerText.toCharArray()) {
            // Skip spaces for this analysis to focus on meaningful characters
            if (c != ' ') {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }
        
        if (frequencyMap.isEmpty()) {
            return ' ';
        }
        
        char mostCommon = ' ';
        int maxCount = 0;
        
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        
        return mostCommon;
    }
    
    /**
     * Calculates the frequency of a specific character in the text.
     * Case-insensitive comparison is used.
     * 
     * @param text The input text
     * @param targetChar The character to count
     * @return Frequency of the target character
     */
    private static int getCharacterFrequency(String text, char targetChar) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        
        char lowerTarget = Character.toLowerCase(targetChar);
        int count = 0;
        
        for (char c : text.toCharArray()) {
            if (Character.toLowerCase(c) == lowerTarget) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Calculates the frequency of a specific word in the text.
     * Case-insensitive comparison is used.
     * 
     * @param text The input text
     * @param targetWord The word to count
     * @return Frequency of the target word
     */
    private static int getWordFrequency(String text, String targetWord) {
        if (text == null || text.isEmpty() || targetWord == null || targetWord.isEmpty()) {
            return 0;
        }
        
        String[] words = text.toLowerCase().split("\\s+");
        String lowerTarget = targetWord.toLowerCase();
        int count = 0;
        
        for (String word : words) {
            // Remove punctuation for more accurate matching
            String cleanWord = word.replaceAll("[^a-zA-Z]", "");
            if (cleanWord.equals(lowerTarget)) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Calculates the number of unique words in the text (case-insensitive).
     * 
     * @param text The input text
     * @return Number of unique words
     */
    private static int getUniqueWordCount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        
        String[] words = text.toLowerCase().split("\\s+");
        Set<String> uniqueWords = new HashSet<>();
        
        for (String word : words) {
            // Remove punctuation for more accurate counting
            String cleanWord = word.replaceAll("[^a-zA-Z]", "");
            if (!cleanWord.isEmpty()) {
                uniqueWords.add(cleanWord);
            }
        }
        
        return uniqueWords.size();
    }
    
    /**
     * Finds the most common word in the text (case-insensitive).
     * 
     * @param text The input text
     * @return The most common word
     */
    private static String getMostCommonWord(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        
        String[] words = text.toLowerCase().split("\\s+");
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "");
            if (!cleanWord.isEmpty()) {
                frequencyMap.put(cleanWord, frequencyMap.getOrDefault(cleanWord, 0) + 1);
            }
        }
        
        if (frequencyMap.isEmpty()) {
            return "";
        }
        
        String mostCommon = "";
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        
        return mostCommon;
    }
    
    /**
     * Prompts the user for a character input with validation.
     * 
     * @param scanner Scanner object for user input
     * @return Validated character
     */
    private static char getValidatedCharacterInput(Scanner scanner) {
        String input = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print("\nEnter a character to check its frequency: ");
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Error: Please enter a character.");
            } else if (input.length() > 1) {
                System.out.println("Error: Please enter only ONE character.");
            } else {
                valid = true;
            }
        }
        
        return input.charAt(0);
    }
    
    /**
     * Prompts the user for a word input with validation.
     * 
     * @param scanner Scanner object for user input
     * @return Validated word
     */
    private static String getValidatedWordInput(Scanner scanner) {
        String input = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print("\nEnter a word to check its frequency: ");
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Error: Please enter a word.");
            } else if (!input.matches("[a-zA-Z]+")) {
                System.out.println("Error: Please enter a valid word containing only letters.");
            } else {
                valid = true;
            }
        }
        
        return input;
    }
}