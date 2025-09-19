import java.util.Scanner;
public class QuizGame {
    public static void main(String[] args)throws Exception {
        Scanner scanner = new Scanner(System.in);
        Administrator admin = new Administrator();
        User user = new User();
        int choice=0;
        do{
            try{
            System.out.println("Welcome to the Quiz Portal!");
            System.out.println("1. User");
            System.out.println("2. Administrator");
            System.out.println("3. Close System");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        user.takeQuiz();
                        break;
                    case 2:
                        System.out.print("Enter administrator password: ");
                        String password = scanner.nextLine();
                        if (admin.authenticate(password)) {
                            admin.manageQuiz();
                        } else {
                            System.out.println("Incorrect password.");
                        }
                        break;
                    case 3:
                        System.out.println("Closing System...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }catch (Exception e){
                System.out.println("Please Enter Integer Value");
                scanner.next();
            }
        }while(choice!=3);
    }
}