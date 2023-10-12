import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Diese Klasse stellt eine Benutzeroberfläche für die Verwaltung eines Tanklagers bereit.
 */
public class TankStorageApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TankStorage tankStorage = new TankStorage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        while (true) {
            //Aktion wählen
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

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    //Tank hinzufügen
                    System.out.println("Geben Sie die Nummer des Tanks an:");
                    int tankNumber = scanner.nextInt();

                    System.out.println("Geben Sie den Namen des Tanks an:");
                    String tankName = scanner.next();

                    System.out.println("Geben Sie die Kapazität des Tanks an:");
                    int tankCapacity = scanner.nextInt();

                    System.out.println("Geben Sie das Erbaudatum des Tanks an (dd.MM.yyyy):");
                    Date constructionDate;
                    try {
                        constructionDate = dateFormat.parse(scanner.next());
                    } catch (ParseException e) {
                        System.out.println("Ungültiges Datumsformat.");
                        break;
                    }

                    Tank newTank = new Tank(tankNumber, tankName, tankCapacity, constructionDate);
                    tankStorage.addTank(newTank);
                    System.out.println("Der Tank wurde erfolgreich hinzugefügt.");
                    break;

                case 2:
                    //Tank löschen
                    System.out.println("Geben Sie die Nummer des Tanks, der entfernt werden soll, an:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int removedTankNumber = scanner.nextInt();
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
                    int maintenanceTankNumber = scanner.nextInt();
                    tankStorage.startMaintenance(maintenanceTankNumber);
                    break;

                case 4:
                    //Wartung beenden
                    System.out.println("Geben Sie die Nummer des Tanks, welcher nicht mehr gewartet werden soll, an:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int endMaintenanceTankNumber = scanner.nextInt();
                    tankStorage.endMaintenance(endMaintenanceTankNumber);
                    break;
                case 5:
                    //Öl anliefern
                    System.out.println("Wie viele Liter werden angeliefert?");
                    int deliveredLiters = scanner.nextInt();
                    int remainingDeliveredLiters = tankStorage.deliverToTanks(deliveredLiters);
                    int successfullyDeliveredLiters = deliveredLiters - remainingDeliveredLiters;
                    System.out.println(successfullyDeliveredLiters + " Liter wurden angeliefert.");
                    break;

                case 6:
                    //Öl ausliefern
                    System.out.println("Wie viele Liter werden ausgeliefert?");
                    int withdrawnLiters = scanner.nextInt();
                    int remainingWithdrawnLiters = tankStorage.withdrawFromTanks(withdrawnLiters);
                    int successfullyWithdrawnLiters = withdrawnLiters - remainingWithdrawnLiters;
                    System.out.println(successfullyWithdrawnLiters + " Liter wurden ausgeliefert.");
                    break;

                case 7:
                    //Infobericht eines Tanks
                    System.out.println("Geben Sie die Nummer des Tanks an, dessen Infobericht Sie wollen:");
                    tankStorage.getTanks().forEach(tank -> System.out.println(tank.getNumber() + " - " + tank.getName()));
                    int tankInfoNumber = scanner.nextInt();
                    System.out.println(tankStorage.getTankInfo(tankInfoNumber));
                    break;

                case 8:
                    //Infobericht des Tanklagers
                    System.out.println(tankStorage.getStorageInfo());
                    break;

                case 9:
                    //Beenden
                    System.out.println("Das Programm wird beendet.");
                    scanner.close();
                    System.exit(0);

                default:
                    //Falls eine Nummer angegeben wird, die keine gültige Aktion hat
                    System.out.println("Diese Aktion existiert nicht.");
                    break;
            }
        }
    }
}
