import java.util.Scanner;

public class QuizGame {
    
    public static void main(String[] args) {
        // Create Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        // Define correct answers for five questions
        // Stored as char primitive data types
        char[] correctAnswers = {'B', 'C', 'A', 'D', 'C'};
        
        // Array to store user's answers
        char[] userAnswers = new char[5];
        
        // Array of questions with options
        String[][] questions = {
            {"1. What is the capital of France?", 
             "A. London", "B. Paris", "C. Berlin", "D. Madrid"},
            {"2. Which planet is known as the Red Planet?", 
             "A. Jupiter", "B. Mars", "C. Venus", "D. Saturn"},
            {"3. What is the largest ocean on Earth?", 
             "A. Pacific Ocean", "B. Atlantic Ocean", "C. Indian Ocean", "D. Arctic Ocean"},
            {"4. Who painted the Mona Lisa?", 
             "A. Vincent van Gogh", "B. Pablo Picasso", "C. Leonardo da Vinci", "D. Claude Monet"},
            {"5. What is the chemical symbol for Gold?", 
             "A. Go", "B. Gd", "C. Au", "D. Ag"}
        };
        
        int correctCount = 0; 
        
        // Display welcome message
        System.out.println("=" .repeat(60));
        System.out.println("          WELCOME TO THE JAVA QUIZ GAME");
        System.out.println("=" .repeat(60));
        System.out.println("Answer each question by typing the letter (A, B, C, or D).\n");
        
        // Loop through each question
        for (int i = 0; i < questions.length; i++) {
            // Display the current question and options
            System.out.println("\n" + questions[i][0]);
            for (int j = 1; j < questions[i].length; j++) {
                System.out.println("   " + questions[i][j]);
            }
            System.out.print("\nYour answer: ");
            
            // Input validation using while loop and if statement
            String input = scanner.nextLine().trim().toUpperCase();
            
            // Validate input - must be a single character between 'A' and 'D'
            while (input.length() != 1 || 
                   (input.charAt(0) != 'A' && input.charAt(0) != 'B' && 
                    input.charAt(0) != 'C' && input.charAt(0) != 'D')) {
                System.out.print("Invalid input! Please enter A, B, C, or D: ");
                input = scanner.nextLine().trim().toUpperCase();
            }
            
            // Store the validated answer
            char answer = input.charAt(0);
            userAnswers[i] = answer;
            
            // Compare answer with correct answer using switch statement
            // Switch statement provides clean multi-way branching
            boolean isCorrect = false;
            switch (answer) {
                case 'A':
                    if (correctAnswers[i] == 'A') isCorrect = true;
                    break;
                case 'B':
                    if (correctAnswers[i] == 'B') isCorrect = true;
                    break;
                case 'C':
                    if (correctAnswers[i] == 'C') isCorrect = true;
                    break;
                case 'D':
                    if (correctAnswers[i] == 'D') isCorrect = true;
                    break;
                default:
                    isCorrect = false;
            }
            
            // Update score and provide feedback
            if (isCorrect) {
                System.out.println("✓ Correct!\n");
                correctCount++;
            } else {
                System.out.println("✗ Incorrect! The correct answer was " 
                                   + correctAnswers[i] + ".\n");
            }
        }
        
        // Compute final score as percentage using arithmetic operators
        // Division operator (/) with cast to double ensures decimal precision
        double percentage = ((double) correctCount / questions.length) * 100;
        
        // Display results summary
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("                 QUIZ RESULTS");
        System.out.println("=" .repeat(60));
        System.out.println("Total Questions: " + questions.length);
        System.out.println("Correct Answers: " + correctCount);
        System.out.println("Incorrect Answers: " + (questions.length - correctCount));
        System.out.printf("Final Score: %.1f%%\n", percentage);
        
        // Provide feedback based on score using if-else multiway branching
        System.out.print("Performance: ");
        if (percentage >= 90) {
            System.out.println("Excellent! Outstanding work!");
        } else if (percentage >= 70) {
            System.out.println("Good job! Well done!");
        } else if (percentage >= 50) {
            System.out.println("Satisfactory. Keep practicing!");
        } else {
            System.out.println("Needs improvement. Review the material and try again!");
        }
        
        // Display detailed answer summary
        System.out.println("\nAnswer Summary:");
        System.out.println("-".repeat(40));
        for (int i = 0; i < questions.length; i++) {
            System.out.printf("Q%d: Your answer: %c | Correct answer: %c | %s\n",
                (i + 1), userAnswers[i], correctAnswers[i],
                (userAnswers[i] == correctAnswers[i]) ? "✓" : "✗");
        }
        
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("Thank you for playing the Java Quiz Game!");
        System.out.println("=" .repeat(60));
        
        // Close the scanner resource
        scanner.close();
    }
}