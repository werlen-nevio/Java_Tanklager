import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TankStorageTest {
    private static Class<?> tankClass;
    private static Class<?> tankStorageClass;
    private static List<Tank> tanks = new ArrayList<>();
    private static TankStorage tankStorage = new TankStorage();

    @BeforeEach
    public void setup() throws Exception {
        int anzahlTanks = 2;

        tankClass = Class.forName("Tank");
        tankStorageClass = Class.forName("TankStorage");

        tanks = new ArrayList<>();
        for (int i = 0; i < anzahlTanks; i++) {
            Tank tank = new Tank(i+1, "Test-Tank", 100.0, LocalDate.now());
            tanks.add(tank);
        }
    }


    /*  ===========================
        Unit Tests für removeTank()
        =======================  */

    @Test
    void removeExistingTank() throws Exception {
        Tank removedTank;
        int tankToRemove = 2;
        int numberOfTanksBefore;
        int numberOfTanksAfter;

        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        numberOfTanksBefore = tankStorage.getTanks().size();
        removedTank = tankStorage.removeTank(tankToRemove);
        numberOfTanksAfter = tankStorage.getTanks().size();

        assertEquals(tankToRemove, removedTank.getNumber());
        assertEquals(1, numberOfTanksBefore-numberOfTanksAfter);
    }

    @Test
    void removeTankEnoughSpace() throws Exception {
        Tank removedTank;
        int tankToRemove = 2;
        int anzahlTanks = 2;
        int numberOfTanksBefore;
        int numberOfTanksAfter;

        tanks = new ArrayList<>();
        for (int i = 0; i < anzahlTanks; i++) {
            Tank tank = new Tank(i+1, "Test-Tank", 100.0, LocalDate.now());
            mockPrivateField(tankClass, tank, "storedLiters", 23.4);
            tanks.add(tank);
        }
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        numberOfTanksBefore = tankStorage.getTanks().size();
        removedTank = tankStorage.removeTank(tankToRemove);
        numberOfTanksAfter = tankStorage.getTanks().size();

        assertEquals(tankToRemove, removedTank.getNumber());
        assertEquals(46.8, tankStorage.getTanks().get(0).getStoredLiters());
        assertEquals(1, numberOfTanksBefore-numberOfTanksAfter);
    }

    @Test
    void removeNonexistentTank() throws Exception {
        Tank removedTank;
        int tankToRemove = 1000;
        int numberOfTanksBefore;
        int numberOfTanksAfter;

        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        numberOfTanksBefore = tankStorage.getTanks().size();
        removedTank = tankStorage.removeTank(tankToRemove);
        numberOfTanksAfter = tankStorage.getTanks().size();

        assertNull(removedTank);
        assertEquals(0, numberOfTanksBefore-numberOfTanksAfter);
    }

    @Test
    void removeTankNotEnoughSpace() throws Exception {
        Tank removedTank;
        int tankToRemove = 2;
        int anzahlTanks = 2;
        int numberOfTanksBefore;
        int numberOfTanksAfter;

        tanks = new ArrayList<>();
        for (int i = 0; i < anzahlTanks; i++) {
            Tank tank = new Tank(i+1, "Test-Tank", 100.0, LocalDate.now());
            mockPrivateField(tankClass, tank, "storedLiters", 75.0);
            tanks.add(tank);
        }
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        numberOfTanksBefore = tankStorage.getTanks().size();
        removedTank = tankStorage.removeTank(tankToRemove);
        numberOfTanksAfter = tankStorage.getTanks().size();

        assertNull(removedTank);
        assertEquals(0, numberOfTanksBefore-numberOfTanksAfter);
    }


    /*  ===============================
        Unit Tests für deliverToTanks()
        ===========================  */

    @Test
    void deliverToTanksValidLiters() throws Exception {
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        assertEquals(0, tankStorage.deliverToTanks(20.0));
    }

    @Test
    void deliverToTanksTooManyLiters() throws Exception {
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        assertEquals(64.0, tankStorage.deliverToTanks(264.0));
    }

    @Test
    void deliverToTanksInvalidLiters() throws Exception {
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        assertEquals(0, tankStorage.deliverToTanks(-20.0));
    }


    /*  ==================================
        Unit Tests für withdrawFromTanks()
        ==============================  */

    @Test
    void withdrawFromTanksValidLiters() throws Exception {
        int anzahlTanks = 2;

        tanks = new ArrayList<>();
        for (int i = 0; i < anzahlTanks; i++) {
            Tank tank = new Tank(i+1, "Test-Tank", 100.0, LocalDate.now());
            mockPrivateField(tankClass, tank, "storedLiters", 75.0);
            tanks.add(tank);
        }
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        assertEquals(0, tankStorage.withdrawFromTanks(80.0));
    }

    @Test
    void withdrawFromTanksTooManyLiters() throws Exception {
        int anzahlTanks = 2;

        tanks = new ArrayList<>();
        for (int i = 0; i < anzahlTanks; i++) {
            Tank tank = new Tank(i+1, "Test-Tank", 100.0, LocalDate.now());
            mockPrivateField(tankClass, tank, "storedLiters", 75.0);
            tanks.add(tank);
        }
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        assertEquals(13.0, tankStorage.withdrawFromTanks(163.0));
    }

    @Test
    void withdrawFromTanksInvalidLiters() throws Exception {
        mockPrivateField(tankStorageClass, tankStorage, "tanks", tanks);

        assertEquals(0, tankStorage.withdrawFromTanks(-20.0));
    }


    /*  =========================
        Hilfsfunktion
        =========================  */

    // Quelle: https://www.baeldung.com/java-mockito-private-fields#enable-mocking-with-java-reflection-api
    void mockPrivateField(Class<?> classBlueprint, Object classObject, String fieldName, Object fieldValue) throws Exception {
        Field fieldStoredLiters = classBlueprint.getDeclaredField(fieldName);
        fieldStoredLiters.setAccessible(true);
        fieldStoredLiters.set(classObject, fieldValue);
    }
}