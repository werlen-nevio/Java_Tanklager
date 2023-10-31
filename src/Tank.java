import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tank fürs Lagern von Öl
 */
class Tank {
    private int number;
    private String name;
    private float capacity;
    private int storedLiters = 0;
    private LocalDate constructionDate;
    private boolean isUnderMaintenance = false;

    /**
     * Initialisiert eine neue Instanz der Tank-Klasse.
     *
     * @param number             Die eindeutige Nummer des Tanks.
     * @param name               Der Name des Tanks.
     * @param capacity         Die maximale Kapazität des Tanks in Litern.
     * @param constructionDate Das Datum, an dem der Tank gebaut wurde.
     */
    public Tank(int number, String name, float capacity, LocalDate constructionDate) {
        this.number = number;
        this.name = name;
        this.capacity = capacity;
        this.constructionDate = constructionDate;

        logCreation();
    }

    /**
     * Liefert eine bestimmte Menge Öl an den Tank.
     *
     * @param liters Die Menge des zu lieferndem Öles in Litern.
     * @return      Die Menge des nicht gelieferten Öles (0, wenn alles geliefert wurde).
     */
    public float deliver(float liters) {
        if (liters <= 0 ) {
            return 0;
        }

        if (isUnderMaintenance) {
            return liters;
        }

        float deliveredLiters = Math.min(liters, capacity - storedLiters);
        storedLiters += deliveredLiters;

        return liters - deliveredLiters;
    }

    /**
     * Entnimmt eine bestimmte Menge Öl aus dem Tank.
     *
     * @param liters Die Menge des zu entnehmenden Öles in Litern.
     * @return      Die Menge des nicht entnommenen Öles (0, wenn alles entnommen wurde).
     */
    public float withdraw(float liters) {
       if (liters <= 0 ) {
            return 0;
        }

        if (isUnderMaintenance) {
            return liters;
        }

        float withdrawnLiters = Math.min(liters, storedLiters);
        storedLiters -= withdrawnLiters;

        return liters - withdrawnLiters;
    }

    /**
     * Gibt Informationen über den Status des Tanks zurück.
     *
     * @return Ein String mit Informationen über den Tank.
     */
    public String getInfo() {
        String status = isUnderMaintenance ? "in Wartung" : storedLiters + " Liter gelagert, freier Platz: " + (capacity - storedLiters) + " Liter";

        return "Der Tank " + name + " (Nr." + number + ", erbaut am " + DateUtils.formatDate(constructionDate) + ") ist " + status;
    }

    /**
     * Gibt den Namen des Tanks zurück.
     *
     * @return Der Name des Tanks.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die aktuelle Menge des im Tank gelagerten Öles zurück.
     *
     * @return Die Menge des gelagerten Öles in Litern.
     */
    public float getStoredLiters() {
        return storedLiters;
    }

    /**
     * Gibt die eindeutige Nummer des Tanks zurück.
     *
     * @return Die Nummer des Tanks.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gibt die maximale Kapazität des Tanks zurück.
     *
     * @return Die maximale Kapazität in Litern.
     */
    public float getCapacity() {
        return capacity;
    }

    /**
     * Überprüft, ob der Tank derzeit in Wartung ist.
     *
     * @return true, wenn der Tank in Wartung ist, sonst false.
     */
    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    /**
     * Setzt den Wartungsstatus des Tanks.
     *
     * @param underMaintenance true, um den Tank in Wartung zu versetzen, false, um die Wartung zu beenden.
     */
    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }


    /**
     * Erstellt einen Logeintrag im Logs/Tank.txt, sobald ein Tank hinzugefügt wird.
     */
    private void logCreation() {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter("Logs/Tank.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Get the current date and time
            Date currentDate = new Date();

            // Write the log entry to the file
            writer.println("Tank created - Number: " + number + ", Name: " + name + ", Capacity: " + capacity + "l, Date: " + currentDate);

            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}