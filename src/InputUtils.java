import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InputUtils {
    static Scanner scanner = new Scanner(System.in);

    public static int userInput_INT() {
        int UserInputINT = 0;
        boolean success = false;

        while (!success) {
            if (scanner.hasNextInt()) {
                UserInputINT = scanner.nextInt();
                success = true;
            } else {
                System.out.println("Bitte geben Sie eine ganze Zahl ein");
                scanner.next(); // Discard invalid input
            }
        }

        return UserInputINT;
    }

    public static float userInput_Float() {
        float userInputFloat = 0.0f;
        boolean success = false;
        Scanner scanner = new Scanner(System.in);

        while (!success) {
            if (scanner.hasNextFloat()) {
                userInputFloat = scanner.nextFloat();
                success = true;
            } else {
                System.out.println("Bitte geben Sie eine Zahl ein");
                scanner.next();
            }
        }

        return userInputFloat;
    }

    public static String userInput_String() {
        String userInputString = "";
        boolean success = false;
        Scanner scanner = new Scanner(System.in);

        while (!success) {
            if (scanner.hasNext()) {
                userInputString = scanner.next();
                success = true;
            } else {
                System.out.println("Bitte geben Sie einen gültigen Wert ein");
                scanner.next();
            }
        }

        return userInputString;
    }

    public static LocalDate userInput_Date() {
        LocalDate userInputDate = null;
        boolean success = false;
        Scanner scanner = new Scanner(System.in);

        while (!success) {
            String userInput = scanner.next();

            try {
                userInputDate = LocalDate.parse(userInput, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                success = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd.MM.yyyy format.");
            }
        }

        return userInputDate;
    }
}