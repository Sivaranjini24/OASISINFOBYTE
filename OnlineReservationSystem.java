import java.util.*;

public class OnlineReservationSystem {

    // Dummy user data for login
    private static final Map<String, String> users = Map.of("user1", "password1", "user2", "password2");
    private static final Scanner scanner = new Scanner(System.in);

    // Reservation data structure
    private static final Map<String, Reservation> reservations = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Online Reservation System!");

        if (!login()) {
            System.out.println("Invalid login credentials. Exiting the system.");
            System.exit(0);
        }

        while (true) {
            System.out.print(
                    "Please Enter:\n1. -> To make a Reservation.\n2. -> To cancel a Reservation.\n3. -> To Exit: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (action) {
                case 1:
                    makeReservation();
                    break;

                case 2:
                    cancelReservation();
                    break;

                case 3:
                    System.out.println("Thank you for using the Online Reservation System!\n.......Program Exiting.......");
                    System.exit(0);

                default:
                    System.out.println("Wrong Input!!!\nEnter Correctly...");
                    break;
            }
        }
    }

    private static boolean login() {
        System.out.println("Login to Online Reservation System");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        return users.containsKey(username) && users.get(username).equals(password);
    }

    private static void makeReservation() {
        System.out.println("Reservation Form");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter Class Type (e.g., First Class, Second Class): ");
        String classType = scanner.nextLine();
        System.out.print("Enter Date of Journey (YYYY-MM-DD): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("Enter From (Place): ");
        String fromPlace = scanner.nextLine();
        System.out.print("Enter To (Destination): ");
        String toDestination = scanner.nextLine();

        String pnr = UUID.randomUUID().toString(); // Generate a random PNR number
        Reservation reservation = new Reservation(name, age, trainNumber, classType, dateOfJourney, fromPlace, toDestination);
        reservations.put(pnr, reservation);

        System.out.println("Reservation Successful!");
        System.out.println("Your PNR Number: " + pnr);
    }

    private static void cancelReservation() {
        System.out.println("Cancellation Form");
        System.out.print("Enter PNR Number: ");
        String pnr = scanner.nextLine();

        if (reservations.containsKey(pnr)) {
            Reservation reservation = reservations.get(pnr);
            System.out.println("Reservation Details:");
            System.out.println(reservation);

            System.out.print("Do you want to confirm the cancellation? (yes/no): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                reservations.remove(pnr);
                System.out.println("Cancellation Successful!");
            } else {
                System.out.println("Cancellation Aborted.");
            }
        } else {
            System.out.println("No reservation found with the given PNR number.");
        }
    }

    // Inner class to represent a reservation
    static class Reservation {
        private String name;
        private int age;
        private String trainNumber;
        private String classType;
        private String dateOfJourney;
        private String fromPlace;
        private String toDestination;

        public Reservation(String name, int age, String trainNumber, String classType, String dateOfJourney, String fromPlace, String toDestination) {
            this.name = name;
            this.age = age;
            this.trainNumber = trainNumber;
            this.classType = classType;
            this.dateOfJourney = dateOfJourney;
            this.fromPlace = fromPlace;
            this.toDestination = toDestination;
        }

        @Override
        public String toString() {
            return "Name: " + name +
                    "\nAge: " + age +
                    "\nTrain Number: " + trainNumber +
                    "\nClass Type: " + classType +
                    "\nDate of Journey: " + dateOfJourney +
                    "\nFrom: " + fromPlace +
                    "\nTo: " + toDestination;
        }
    }
}
