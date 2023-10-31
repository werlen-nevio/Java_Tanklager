import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Diese Klasse stellt eine Benutzeroberfläche für die Verwaltung eines Tanklagers bereit.
 */
public class TankStorageApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TankStorage tankStorage = new TankStorage();

        logStart();
        //Beim ersten Mal nicht Aktion wählen, sondern direkt zu Tank hinzufügen.
        boolean SkipAktion = true;

        while (true) {
            int action;
            if(SkipAktion){
                action = 1;
            }else {
                //Aktion wählen
                System.out.println();
                System.out.println("Wähle eine Aktion:");
                System.out.println("1| Tank hinzufügen");
                System.out.println("2| Tank entfernen");
                System.out.println("3| Wartung starten");
                System.out.println("4| Wartung beenden");
                System.out.println("5| Öl anliefern");
                System.out.println("6| Öl ausliefern");
                System.out.println("7| Infobericht eines Tanks");
                System.out.println("8| Infobericht des Tanklagers");
                System.out.println("9| Beenden");

                action = InputUtils.userInput_INT();
            }

            switch (action) {
                case 1:
                    //Tank hinzufügen
                    System.out.println("Geben Sie die Nummer des Tanks an:");
                    int tankNumber = InputUtils.userInput_INT();

                    System.out.println("Geben Sie den Namen des Tanks an:");
                    String tankName = InputUtils.userInput_String();

                    System.out.println("Geben Sie die Kapazität des Tanks in Liter an:");
                    float tankCapacity = InputUtils.userInput_Float();

                    System.out.println("Geben Sie das Erbaudatum des Tanks an (dd.MM.yyyy):");
                    LocalDate constructionDate;
                    constructionDate = InputUtils.userInput_Date();

                    Tank newTank = new Tank(tankNumber, tankName, tankCapacity, constructionDate);
                    tankStorage.addTank(newTank);
                    System.out.println("Der Tank wurde erfolgreich hinzugefügt.");
                    break;

                case 2:
                    //Tank löschen
                    System.out.println("Geben Sie die Nummer des Tanks, der entfernt werden soll, an:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int removedTankNumber = InputUtils.userInput_INT();
                    Tank removedTank = tankStorage.removeTank(removedTankNumber);

                    if (removedTank == null) {
                        System.out.println("Kein Tank wurde entfernt.");
                    } else {
                        System.out.println("Folgender Tank wurde erfolgreich entfernt:");
                        System.out.println(removedTank.getInfo());
                    }
                    break;

                case 3:
                    //Wartung starten
                    System.out.println("Geben Sie die Nummer des Tanks, welcher gewartet werden soll, an:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int maintenanceTankNumber = InputUtils.userInput_INT();
                    tankStorage.startMaintenance(maintenanceTankNumber);
                    break;

                case 4:
                    //Wartung beenden
                    System.out.println("Geben Sie die Nummer des Tanks, welcher nicht mehr gewartet werden soll, an:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int endMaintenanceTankNumber = InputUtils.userInput_INT();
                    tankStorage.endMaintenance(endMaintenanceTankNumber);
                    break;

                case 5:
                    //Öl anliefern
                    System.out.println("Wie viele Liter werden angeliefert?");
                    float deliveredLiters = InputUtils.userInput_Float();
                    float remainingDeliveredLiters = tankStorage.deliverToTanks(deliveredLiters);
                    float successfullyDeliveredLiters = deliveredLiters - remainingDeliveredLiters;
                    System.out.println(successfullyDeliveredLiters + " Liter wurden angeliefert.");
                    logDelivery(successfullyDeliveredLiters);
                    break;

                case 6:
                    //Öl ausliefern
                    System.out.println("Wie viele Liter werden ausgeliefert?");
                    float withdrawnLiters = InputUtils.userInput_Float();
                    float remainingWithdrawnLiters = tankStorage.withdrawFromTanks(withdrawnLiters);
                    float successfullyWithdrawnLiters = withdrawnLiters - remainingWithdrawnLiters;
                    System.out.println(successfullyWithdrawnLiters + " Liter wurden ausgeliefert.");
                    logWithdraw(successfullyWithdrawnLiters);
                    break;

                case 7:
                    //Infobericht eines Tanks
                    System.out.println("Geben Sie die Nummer des Tanks an, dessen Infobericht Sie wollen:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int tankInfoNumber = InputUtils.userInput_INT();
                    System.out.println(tankStorage.getTankInfo(tankInfoNumber));
                    break;

                case 8:
                    //Infobericht des Tanklagers
                    System.out.println(tankStorage.getStorageInfo());
                    break;

                case 9:
                    //Beenden
                    System.out.println("Das Programm wird beendet.");
                    logExit();
                    System.exit(0);

                default:
                    //Falls eine Nummer angegeben wird, die keine gültige Aktion hat
                    System.out.println("Diese Aktion existiert nicht.");
                    break;
            }
            SkipAktion = false;
        }
    }

    /**
     * Erstellt einen Logeintrag im Logs/Deliver.txt, sobald Öl angeliefert wird.
     */
    private static void logDelivery(float successfullyDeliveredLiters){
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Deliver.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Delivered - Liters: "+ successfullyDeliveredLiters +", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen Logeintrag im Logs/Withdraw.txt, sobald Öl ausgeliefert wird.
     */
    private static void logWithdraw(float successfullyWithdrawnLiters){
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Withdraw.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Withdrawn - Liters: "+ successfullyWithdrawnLiters +", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen Logeintrag in allen Logfiles, sobald die Applikation beendet wird.
     */
    private static void logExit(){
        // Get the current date and time
        Date currentDate = new Date();
        String LogText = "Applikation beendet - Date: " + currentDate;

        //Nachricht in alle Logfiles schreiben
        logInAllFiles(LogText);
    }

    /**
     * Erstellt einen Logeintrag in allen Logfiles, sobald die Applikation gestartet wird.
     */
    private static void logStart(){
        // Get the current date and time
        Date currentDate = new Date();
        String LogText = "Applikation gestartet - Date: " + currentDate;

        //Nachricht in alle Logfiles schreiben
        logInAllFiles(LogText);
    }

    /**
     * Erstellt einen Logeintrag in allen Logfiles benötigt eine Nachricht
     */
    private static void logInAllFiles(String Nachricht){
        //logfile In Deliver.txt
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Deliver.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Write the log entry to the file
            writer.println(Nachricht);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //logfile In Info.txt
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Info.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Write the log entry to the file
            writer.println(Nachricht);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //logfile In Maintenance.txt
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Maintenance.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Write the log entry to the file
            writer.println(Nachricht);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //logfile In Tank.txt
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Tank.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Write the log entry to the file
            writer.println(Nachricht);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //logfile In Withdraw.txt
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Withdraw.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Write the log entry to the file
            writer.println(Nachricht);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
