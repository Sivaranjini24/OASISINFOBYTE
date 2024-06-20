import java.util.*;
import java.util.concurrent.*;

public class OnlineExaminationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> profiles = new HashMap<>();
    private static final Map<String, String> questions = new HashMap<>();
    private static String currentUser;

    public static void main(String[] args) {
        System.out.println("Welcome to the Online Examination System!");

        initializeSystem();

        if (!login()) {
            System.out.println("Invalid login credentials. Exiting the system.");
            System.exit(0);
        }

        while (true) {
            System.out.print(
                    "Please Enter:\n1. -> Update Profile and Password.\n2. -> Start MCQ Test.\n3. -> Logout: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (action) {
                case 1:
                    updateProfile();
                    break;

                case 2:
                    startTest();
                    break;

                case 3:
                    logout();
                    System.exit(0);

                default:
                    System.out.println("Wrong Input!!!\nEnter Correctly...");
                    break;
            }
        }
    }

    private static void initializeSystem() {
        // Initialize dummy users
        users.put("user1", "password1");
        users.put("user2", "password2");
        profiles.put("user1", "user1 profile");
        profiles.put("user2", "user2 profile");

        // Initialize questions
        questions.put("1", "What is the capital of France?\nA. Berlin\nB. Madrid\nC. Paris\nD. Rome");
        questions.put("2", "What is 2 + 2?\nA. 3\nB. 4\nC. 5\nD. 6");
    }

    private static boolean login() {
        System.out.println("Login to Online Examination System");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            return true;
        } else {
            return false;
        }
    }

    private static void updateProfile() {
        System.out.println("Update Profile");
        System.out.print("Enter new profile info: ");
        String profile = scanner.nextLine();
        profiles.put(currentUser, profile);
        System.out.println("Profile updated successfully!");

        System.out.print("Do you want to update the password? (yes/no): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            users.put(currentUser, newPassword);
            System.out.println("Password updated successfully!");
        }
    }

    private static void startTest() {
        System.out.println("Starting MCQ Test");
        System.out.println("You have 5 minutes to complete the test.");
        System.out.println("Press Enter to start the timer.");
        scanner.nextLine();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Future<?> future = executor.submit(() -> {
            for (String question : questions.keySet()) {
                System.out.println(questions.get(question));
                System.out.print("Your answer: ");
                String answer = scanner.nextLine();
            }
            System.out.println("Test completed!");
        });

        executor.schedule(() -> {
            if (!future.isDone()) {
                future.cancel(true);
                System.out.println("\nTime's up! The test is automatically submitted.");
            }
            executor.shutdown();
        }, 5, TimeUnit.MINUTES);

        try {
            future.get(5, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            future.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void logout() {
        System.out.println("Thank you for using the Online Examination System!\n.......Logging out.......");
        currentUser = null;
    }
}
