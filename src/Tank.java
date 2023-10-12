import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tank fürs Lagern von Öl
 */
class Tank {
    private int number;
    private String name;
    private int capacity;
    private int storedLiters = 0;
    private Date constructionDate;
    private boolean isUnderMaintenance = false;

    /**
     * Initialisiert eine neue Instanz der Tank-Klasse.
     *
     * @param number             Die eindeutige Nummer des Tanks.
     * @param name               Der Name des Tanks.
     * @param capacity         Die maximale Kapazität des Tanks in Litern.
     * @param constructionDate Das Datum, an dem der Tank gebaut wurde.
     */
    public Tank(int number, String name, int capacity, Date constructionDate) {
        this.number = number;
        this.name = name;
        this.capacity = capacity;
        this.constructionDate = constructionDate;
    }

    /**
     * Liefert eine bestimmte Menge Öl an den Tank.
     *
     * @param liters Die Menge des zu lieferndem Öles in Litern.
     * @return      Die Menge des nicht gelieferten Öles (0, wenn alles geliefert wurde).
     */
    public int deliver(int liters) {
        if (liters <= 0 || isUnderMaintenance) {
            return 0;
        }

        int deliveredLiters = Math.min(liters, capacity - storedLiters);
        storedLiters += deliveredLiters;

        return liters - deliveredLiters;
    }

    /**
     * Entnimmt eine bestimmte Menge Öl aus dem Tank.
     *
     * @param liters Die Menge des zu entnehmenden Öles in Litern.
     * @return      Die Menge des nicht entnommenen Öles (0, wenn alles entnommen wurde).
     */
    public int withdraw(int liters) {
        if (liters <= 0 || isUnderMaintenance) {
            return 0;
        }

        int withdrawnLiters = Math.min(liters, storedLiters);
        storedLiters -= withdrawnLiters;

        return liters - withdrawnLiters;
    }

    /**
     * Gibt Informationen über den Status des Tanks zurück.
     *
     * @return Ein String mit Informationen über den Tank.
     */
    public String getInfo() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String status = isUnderMaintenance ? "in Wartung" : storedLiters + " Liter gelagert, freier Platz: " + (capacity - storedLiters) + " Liter";

        return "Der Tank " + name + " (Nr." + number + ", erbaut am " + dateFormat.format(constructionDate) + ") ist " + status;
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
    public int getStoredLiters() {
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
    public int getCapacity() {
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
}