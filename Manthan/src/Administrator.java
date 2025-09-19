import java.util.InputMismatchException;
import java.util.Scanner;
public class Administrator {
    private static final String PASSWORD = "Quiz2024";
    private DatabaseManager dbManager = new DatabaseManager();
    User user=new User();
    public boolean authenticate(String inputPassword) {
        return PASSWORD.equals(inputPassword);
    }

    public void manageQuiz() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice=0;
            do{
                try{
                System.out.println("Administrator Menu:");
                System.out.println("1. Add Question");
                System.out.println("2. Remove Question");
                System.out.println("3. Update Question");
                System.out.println("4. User Record");
                System.out.println("5. User Percentage");
                System.out.println("6. Back to Main Menu");
                System.out.print("Choose an option: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addQuestion();
                    break;
                case 2:
                    removeQuestion();
                    break;
                case 3:
                    updateQuestion();
                    break;
                case 4:
                    UserHistory();
                    break;
                case 5:
                try{
                    System.out.print("Enter your username: ");
                    String pusername = scanner.nextLine();
                    System.out.print("Enter category [dbms / fee / maths / java]: ");
                    String pcategory = scanner.nextLine();
                    percentage(pusername, pcategory);
                }catch(InputMismatchException e){
                    System.out.println("Invalid Category");
                }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }catch(Exception e){
            System.out.println("Invalid Enter Integer Value.");
            scanner.next();
        }
    }while(choice!=4);
}

    private void addQuestion() {
        Scanner scanner = new Scanner(System.in);
        try {
        System.out.print("Enter category [dbms / fee / maths / java]: ");
        String category = scanner.nextLine();
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter option 1: ");
        String option1 = scanner.nextLine();
        System.out.print("Enter option 2: ");
        String option2 = scanner.nextLine();
        System.out.print("Enter option 3: ");
        String option3 = scanner.nextLine();
        System.out.print("Enter option 4: ");
        String option4 = scanner.nextLine();
        System.out.print("Enter correct answer: ");
        String correctAnswer=null;
        
            correctAnswer = scanner.nextLine();
            char ans = correctAnswer.charAt(0);
            if(Character.isDigit(ans)){
                System.out.println("Invalid input");
                return;
            }
        

        Question newQuestion = new Question(question, option1, option2, option3, option4, correctAnswer);
        dbManager.addQuestionToDatabase(newQuestion, category);
        System.out.println("Question added successfully.");
            
    } catch (Exception e) {
        System.out.println("Enter Valid Input");
        System.out.println("Pree 2 Back To Admin ");
        scanner.next();
    }
    }
    private void removeQuestion() {
        Scanner scanner = new Scanner(System.in);
        try{
        System.out.print("Enter category [dbms / fee / maths / java]: ");
        String category = scanner.nextLine();
        System.out.print("Enter question ID to remove: ");
        int questionId = scanner.nextInt();

        dbManager.removeQuestionFromDatabase(questionId, category);
        System.out.println("Question removed successfully.");
    } catch (Exception e) {
        System.out.println("Enter Valid Input");
        scanner.next();
    }
    }
    private void UserHistory(){
        Scanner scanner = new Scanner(System.in);
        try{
        dbManager.DataHistory();
        System.out.println("User History:");
        }catch(Exception e){
            System.out.println("Ent Valid Input");
            System.out.println("Error: "+e.getMessage());
        }
    }
    public void percentage(String username,String category){
        dbManager.getPercentage(username, category);
    }
    private void updateQuestion() {
        Scanner scanner = new Scanner(System.in);
        try{
        System.out.print("Enter category [dbms / fee / maths / java]: ");
        String category = scanner.nextLine();
        System.out.print("Enter question ID to update: ");
        int questionId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter new question: ");
        String newQuestion = scanner.nextLine();
        System.out.print("Enter new option 1: ");
        String newOption1 = scanner.nextLine();
        System.out.print("Enter new option 2: ");
        String newOption2 = scanner.nextLine();
        System.out.print("Enter new option 3: ");
        String newOption3 = scanner.nextLine();
        System.out.print("Enter new option 4: ");
        String newOption4 = scanner.nextLine();
        System.out.print("Enter new correct answer: ");
        String newCorrectAnswer = scanner.nextLine();

        Question updatedQuestion = new Question(newQuestion, newOption1, newOption2, newOption3, newOption4, newCorrectAnswer);
        dbManager.updateQuestionInDatabase(updatedQuestion, category, questionId);
        System.out.println("Question updated");
    }catch (Exception e) {
        System.out.println("Enter Valid Input");
        scanner.next();
    }
}
}