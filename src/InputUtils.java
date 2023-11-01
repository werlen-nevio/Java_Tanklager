import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Die InputUtils-Klasse enthält Hilfsfunktionen zur Eingabe von Validieren vom Inputs.
 */
public class InputUtils {
    static Scanner scanner = new Scanner(System.in);

    /**
     * Liest eine ganze Zahl (int) von der Benutzereingabe ein.
     *
     * @return Die eingegebene ganze Zahl.
     */
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

    /**
     * Liest eine Zahl (double) von der Benutzereingabe ein.
     *
     * @return Die eingegebene Double-Zahl.
     */
    public static double userInput_Double() {
        double userInputDouble = 0.0;
        boolean success = false;
        Scanner scanner = new Scanner(System.in);

        while (!success) {
            if (scanner.hasNextDouble()) {
                userInputDouble = scanner.nextDouble();
                success = true;
            } else {
                System.out.println("Bitte geben Sie eine Zahl ein");
                scanner.next();
            }
        }

        return userInputDouble;
    }

    /**
     * Liest einen String von der Benutzereingabe ein.
     *
     * @return Die eingegebene Zeichenkette.
     */
    public static String userInput_String() {
        String userInputString = "";
        boolean success = false;
        Scanner scanner = new Scanner(System.in);

        while (!success) {
            if (scanner.hasNext()) {
                userInputString = scanner.nextLine();
                success = true;
            } else {
                System.out.println("Bitte geben Sie einen gültigen Wert ein");
                scanner.next();
            }
        }

        return userInputString;
    }

    /**
     * Liest ein Datum im Format "dd.MM.yyyy" von der Benutzereingabe ein.
     *
     * @return Das eingegebene Datum als LocalDate-Objekt.
     */
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