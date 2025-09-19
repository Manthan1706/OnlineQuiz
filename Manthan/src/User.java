import java.util.List;
import java.util.Scanner;

public class User {
    private DatabaseManager dbManager = new DatabaseManager();

    public void takeQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        try{
        System.out.print("Choose quiz category [dbms / fee / maths / java]: ");
        String category = scanner.nextLine();

        List<Question> questions = dbManager.getQuestions(category);
        if (questions.isEmpty()) {
            System.out.println("No questions available in this category.");
            return;
        }
    
        CustomQueue<Question> queue = new CustomQueue<>();
        for (Question q : questions) {
            queue.enqueue(q);
        }
        // queue.shuffle();
    
        int score = 0;
        while (!queue.isEmpty()) {
            Question question = queue.dequeue();
            System.out.println(question.getQuestion());
            System.out.println("A. " + question.getOption1());
            System.out.println("B. " + question.getOption2());
            System.out.println("C. " + question.getOption3());
            System.out.println("D. " + question.getOption4());
            System.out.print("Answer - (A/B/C/D): ");
            String answer = scanner.nextLine().trim().toUpperCase().toLowerCase();

            boolean isValidChoice = answer.equals('A') || answer.equals('B') || answer.equals('C') || answer.equals('D') ||
            answer.equals('a') || answer.equals('b') || answer.equals('c') || answer.equals('d');

            System.out.println("Answer "+question.getCorrectAnswer() + "is  correct");
            if (isValidChoice) {
                System.out.println("Invalid choice. This answer will be marked as incorrect.");
            } else if (question.getCorrectAnswer().equalsIgnoreCase(answer)) {
                score++;
            }else{
                System.out.println("Your answer is incorrect. Correct answer is : " + question.getCorrectAnswer());
            }
        }
        System.out.println("Quiz completed. Your score: " + score);
        System.out.println();
        dbManager.saveUserScore(username, category, score);
    } catch(Exception e) {
        System.out.println("Enter Valid : " + e.getMessage());
    }
}
    private int getOptionIndex(String choice) {
        try{
        switch (choice) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1; // This should never happen due to the validation
        }
    }catch(Exception e){
        System.out.println("Invalid option. Please try again.");
        return -1;
    }
    }
}