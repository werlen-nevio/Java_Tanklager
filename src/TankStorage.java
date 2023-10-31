import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Diese Klasse repräsentiert ein Tanklager, das mehrere Tanks zur Speicherung von Öl enthält.
 */
class TankStorage {
    private List<Tank> tanks = new ArrayList<>();

    /**
     * Fügt dem Tanklager einen Tank hinzu.
     *
     * @param tank Der hinzuzufügende Tank.
     */
    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    /**
     * Entfernt einen Tank aus dem Tanklager anhand seiner Tanknummer.
     *
     * @param tankNumber Die Nummer des zu entfernenden Tanks.
     * @return           Der entfernte Tank oder null, wenn kein Tank mit der angegebenen Nummer gefunden wurde.
     */
    public Tank removeTank(int tankNumber) {
        Tank removedTank = null;
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                removedTank = tank;
                tanks.remove(tank);
                break;
            }
        }
        logRemoval(tankNumber);
        return removedTank;
    }

    /**
     * Liefert eine bestimmte Menge Öl an das Tanklager.
     *
     * @param liters Die Menge des zu lieferndem Öles in Litern.
     * @return Die Menge des nicht gelieferten Öles (0, wenn alles geliefert wurde).
     */
    public float deliverToTanks(float liters) {
        float remainingLiters = liters;
        for (Tank tank : tanks) {
            remainingLiters = tank.deliver(remainingLiters);
        }
        generateStatusMessage("geliefert", liters - remainingLiters);
        return remainingLiters;
    }

    /**
     * Entnimmt eine bestimmte Menge Öl aus allen Tanks im Tanklager.
     *
     * @param liters Die Menge des zu entnehmenden Öles in Litern.
     * @return Die Menge des nicht entnommenen Öles (0, wenn alles entnommen wurde).
     */
    public float withdrawFromTanks(float liters) {
        float remainingLiters = liters;
        for (Tank tank : tanks) {
            remainingLiters = tank.withdraw(remainingLiters);
        }
        generateStatusMessage("entnommen", liters - remainingLiters);
        return remainingLiters;
    }

    /**
     * Startet die Wartung für einen bestimmten Tank im Tanklager.
     *
     * @param tankNumber Die Nummer des Tanks, der in Wartung versetzt werden soll.
     */
    public void startMaintenance(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                tank.setUnderMaintenance(true);
                break;
            }
        }
        logstartMaintenance(tankNumber);
    }

    /**
     * Beendet die Wartung für einen bestimmten Tank im Tanklager.
     *
     * @param tankNumber Die Nummer des Tanks, dessen Wartung beendet werden soll.
     */
    public void endMaintenance(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                tank.setUnderMaintenance(false);
                break;
            }
        }
        logendMaintenance(tankNumber);
    }

    /**
     * Gibt Informationen über den Gesamtstatus des Tanklagers zurück.
     *
     * @return Ein String mit Informationen über das Tanklager.
     */
    public String getStorageInfo() {
        int totalTanks = tanks.size();
        float totalCapacity = (float) tanks.stream().mapToDouble(tank -> tank.getCapacity()).sum();
        float totalStoredLiters = (float) tanks.stream().mapToDouble(tank -> tank.getStoredLiters()).sum();
        float totalFreeCapacity = totalCapacity - totalStoredLiters;
        String Message = "Das Tanklager umfasst " + totalTanks + " Tanks mit einer Gesamtkapazität von " + totalCapacity + " Litern. " +
                totalStoredLiters + " Liter Öl sind eingelagert. Für " + totalFreeCapacity + " Liter gibt es noch Platz.";
        logStorageInfo(Message);

        return Message;
    }

    /**
     * Gibt den Infobericht eines bestimmten Tanks im Tanklager zurück.
     *
     * @param tankNumber Die Nummer des Tanks, dessen Infobericht angefordert wird.
     * @return Der Infobericht des angegebenen Tanks oder eine Meldung, wenn der Tank nicht gefunden wurde.
     */
    public String getTankInfo(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                logTankInfo(tank.getInfo());
                return tank.getInfo();
            }
        }
        String Message = "Tank mit Nummer " + tankNumber + " wurde nicht gefunden.";
        logTankInfo(Message);
        return Message;
    }

    /**
     * Gibt eine Liste aller Tanks im Tanklager zurück.
     *
     * @return Eine Liste aller Tanks im Tanklager.
     */
    public List<Tank> getTanks() {
        return tanks;
    }

    /**
     * Erstellt einen Logeintrag im Logs/Tank.txt, sobald ein Tank gelöscht wird.
     */
    private void logRemoval(int tankNumber) {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Tank.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Tank deleted - Number: " + tankNumber + ", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen Logeintrag im Logs/Maintenance.txt, sobald eine Wartung gestartet wird.
     */
    private  void logstartMaintenance(int tankNumber) {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Maintenance.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Maintenance start - Number: " + tankNumber + ", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen Logeintrag im Logs/Maintenance.txt, sobald eine Wartung beendet wird.
     */
    private  void logendMaintenance(int tankNumber) {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Maintenance.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Maintenance end - Number: " + tankNumber + ", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen Logeintrag im Logs/Info.txt, sobald StorageInfo angefragt wird.
     */
    private  void logStorageInfo(String Message) {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Info.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Get StorageInfo - Message: " + Message + ", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen Logeintrag im Logs/Info.txt, sobald TankInfo angefragt wird.
     */
    private  void logTankInfo(String Message) {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Info.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Get TankInfo - Message: " + Message + ", Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generiert eine Statusmeldung und gibt sie in der Konsole aus.
     *
     * @param message Die Statusmeldung.
     */
    private void printStatusMessage(String message) {
        System.out.println(message);
    }

    /**
     * Erstellt eine Statusmeldung und gibt sie in der Konsole aus.
     *
     * @param operation Die Operation (Lieferung oder Entnahme).
     * @param liters    Die Menge des gelieferten oder entnommenen Öles in Litern.
     */
    private void generateStatusMessage(String operation, float liters) {
        StringBuilder message = new StringBuilder("Öl " + operation + ": " + liters + " Liter\n");

        for (Tank tank : tanks) {
            message.append(tank.getInfo()).append("\n");
        }

        printStatusMessage(message.toString());
    }
}