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
     * Entfernt einen Tank aus dem Tanklager anhand seiner Tanknummer und verteilt seinen Inhalt um.
     *
     * @param tankNumber Die Nummer des zu entfernenden Tanks.
     * @return Der entfernte Tank oder null, wenn kein Tank mit der angegebenen Nummer gefunden wurde.
     */
    public Tank removeTank(int tankNumber) {
        Tank removedTank = null;
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                double removedLiters = tank.getStoredLiters();
                boolean canRemove = true;

                double spaceAvailable = 0;
                for (Tank otherTank : tanks) {
                    if (otherTank != tank) {
                        spaceAvailable += otherTank.getCapacity() - otherTank.getStoredLiters();
                    }
                }
                if (spaceAvailable < removedLiters) {
                    canRemove = false;
                }

                if (canRemove) {
                    double remainingLiter = removedLiters;
                    for (Tank otherTank : tanks) {
                        if (otherTank != tank) {
                            remainingLiter = otherTank.deliver(remainingLiter);
                        }
                    }

                    tanks.remove(tank);
                    removedTank = tank;

                    logRedistributeContent(tankNumber, removedLiters);
                } else {
                    System.out.println("Der Tank kann nicht entfernt werden, da nicht genügend Platz zum Umverteilen vorhanden ist.");
                }
                logRemoval(tankNumber);
                break;
            }
        }
        return removedTank;
    }

    /**
     * Erstellt einen Logeintrag im Logs/ContentRedistribution.txt, sobald der Inhalt eines Tanks umverteilt wird.
     */
    private void logRedistributeContent(int tankNumber, double redistributedLiters) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/ContentRedistribution.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Content Redistribution - From Tank: " + tankNumber + ", Liters: " + redistributedLiters + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Liefert eine bestimmte Menge Öl an das Tanklager.
     *
     * @param liters Die Menge des zu lieferndem Öles in Litern.
     * @return Die Menge des nicht gelieferten Öles (0, wenn alles geliefert wurde).
     */
    public double deliverToTanks(double liters) {
        if (liters <= 0) {
            generateStatusMessage("geliefert", 0);
            return 0;
        }

        double remainingLiters = liters;
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
    public double withdrawFromTanks(double liters) {
        if (liters <= 0) {
            generateStatusMessage("entnommen", 0);
            return 0;
        }
        double remainingLiters = liters;
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
                double tankContent = tank.getStoredLiters();
                boolean canStartMaintenance = true;

                double spaceAvailable = 0;
                for (Tank otherTank : tanks) {
                    if (otherTank != tank) {
                        spaceAvailable += otherTank.getCapacity() - otherTank.getStoredLiters();
                    }
                }
                if (spaceAvailable < tankContent) {
                    canStartMaintenance = false;
                }

                if (canStartMaintenance) {
                    // Umverteilung des Inhalts, bevor die Wartung beginnt
                    double remainingLiter = tankContent;
                    for (Tank otherTank : tanks) {
                        if (otherTank != tank) {
                            remainingLiter = otherTank.deliver(remainingLiter);
                        }
                    }

                    // Setzen des Tanks in Wartung, nachdem der Inhalt umverteilt wurde
                    tank.setUnderMaintenance(true);

                    // Log-Eintrag für den Start der Wartung
                    logstartMaintenance(tankNumber);

                    // Log-Eintrag für die Umverteilung des Inhalts
                    logRedistributeContent(tankNumber, tankContent);

                    // Feedback in der Konsole
                    System.out.println("Wartung für Tank " + tank.getName() + " (Nr." + tank.getNumber() + ") wurde gestartet.");
                } else {
                    System.out.println("Die Wartung kann nicht gestartet werden, da nicht genügend Platz zum Umverteilen vorhanden ist.");
                }
                break;
            }
        }
    }


    /**
     * Beendet die Wartung für einen bestimmten Tank im Tanklager.
     *
     * @param tankNumber Die Nummer des Tanks, dessen Wartung beendet werden soll.
     */
    public void endMaintenance(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                // Setzen des Tanks aus der Wartung und Zurücksetzen der gelagerten Liter auf 0
                tank.setUnderMaintenance(false);
                tank.setStoredLiters(0); // Setze gelagerte Liter auf 0

                // Log-Eintrag für das Ende der Wartung
                logendMaintenance(tankNumber);

                // Feedback in der Konsole
                System.out.println("Wartung für Tank " + tank.getName() + " (Nr." + tank.getNumber() + ") wurde beendet.");
                break;
            }
        }
    }

    /**
     * Gibt Informationen über den Gesamtstatus des Tanklagers zurück.
     *
     * @return Ein String mit Informationen über das Tanklager.
     */
    public String getStorageInfo() {
        int totalTanks = 0;
        double totalCapacity = 0;
        double totalStoredLiters = 0;

        for (Tank tank : tanks) {
            if (!tank.isUnderMaintenance()) {
                totalTanks++;
                totalCapacity += tank.getCapacity();
                totalStoredLiters += tank.getStoredLiters();
            }
        }

        double totalFreeCapacity = totalCapacity - totalStoredLiters;
        String message = "Das Tanklager umfasst " + totalTanks + " Tanks mit einer Gesamtkapazität von " + totalCapacity + " Litern. " +
                totalStoredLiters + " Liter Öl sind eingelagert. Für " + totalFreeCapacity + " Liter gibt es noch Platz.";
        logStorageInfo(message);

        return message;
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
    private void logstartMaintenance(int tankNumber) {
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
    private void logendMaintenance(int tankNumber) {
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
    private void logStorageInfo(String Message) {
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
    private void logTankInfo(String Message) {
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
    private void generateStatusMessage(String operation, double liters) {
        StringBuilder message = new StringBuilder("Öl " + operation + ": " + liters + " Liter\n");

        for (Tank tank : tanks) {
            message.append(tank.getInfo()).append("\n");
        }

        printStatusMessage(message.toString());
    }
}