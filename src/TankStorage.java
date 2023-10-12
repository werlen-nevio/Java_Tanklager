import java.util.ArrayList;
import java.util.List;

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
        return removedTank;
    }

    /**
     * Liefert eine bestimmte Menge Öl an das Tanklager.
     *
     * @param liters Die Menge des zu lieferndem Öles in Litern.
     * @return       Die Menge des nicht geliefertem Öles (0, wenn alles geliefert wurde).
     */
    public int deliverToTanks(int liters) {
        int remainingLiters = liters;
        for (Tank tank : tanks) {
            remainingLiters = tank.deliver(remainingLiters);
        }
        return remainingLiters;
    }

    /**
     * Entnimmt eine bestimmte Menge Öl aus allen Tanks im Tanklager.
     *
     * @param liters Die Menge des zu entnehmenden Öles in Litern.
     * @return       Die Menge des nicht entnommenen Öles (0, wenn alles entnommen wurde).
     */
    public int withdrawFromTanks(int liters) {
        int remainingLiters = liters;
        for (Tank tank : tanks) {
            remainingLiters = tank.withdraw(remainingLiters);
        }
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
    }

    /**
     * Gibt Informationen über den Gesamtstatus des Tanklagers zurück.
     *
     * @return Ein String mit Informationen über das Tanklager.
     */
    public String getStorageInfo() {
        int totalTanks = tanks.size();
        int totalCapacity = tanks.stream().mapToInt(Tank::getCapacity).sum();
        int totalStoredLiters = tanks.stream().mapToInt(Tank::getStoredLiters).sum();
        int totalFreeCapacity = totalCapacity - totalStoredLiters;

        return "Das Tanklager umfasst " + totalTanks + " Tanks mit einer Gesamtkapazität von " + totalCapacity + " Litern. " +
                totalStoredLiters + " Liter Öl sind eingelagert. Für " + totalFreeCapacity + " Liter gibt es noch Platz.";
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
                return tank.getInfo();
            }
        }
        return "Tank mit Nummer " + tankNumber + " wurde nicht gefunden.";
    }

    /**
     * Gibt eine Liste aller Tanks im Tanklager zurück.
     *
     * @return Eine Liste aller Tanks im Tanklager.
     */
    public List<Tank> getTanks() {
        return tanks;
    }
}

