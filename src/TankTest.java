import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TankTest {
    private static Class<?> tankClass;
    private static Tank tank;

    @BeforeAll
    public static void setup() throws Exception {
        tankClass = Class.forName("Tank");
        tank = new Tank(1, "Test-Tank", 100.0, LocalDate.now());
    }


    /*  ========================
        Unit Tests für deliver()
        ========================  */

    @Test
    void deliverValidLiters() throws Exception {
        mockPrivateField(tankClass, tank, "capacity", 100.0);
        mockPrivateField(tankClass, tank, "storedLiters", 1.0);

        assertEquals(0.0, tank.deliver(15.0));
        assertEquals(16.0, tank.getStoredLiters());
    }

    @Test
    void deliverInvalidLiters() {
        assertEquals(0.0, tank.deliver(-15.0));
    }

    @Test
    void deliverToTankInMaintenance() throws Exception {
        mockPrivateField(tankClass, tank, "isUnderMaintenance", true);

        assertEquals(50.0, tank.deliver(50.0));
    }

    @Test
    void deliverToFullTank() throws Exception {
        mockPrivateField(tankClass, tank, "capacity", 100.0);
        mockPrivateField(tankClass, tank, "storedLiters", 100.0);

        assertEquals(50.0, tank.deliver(50.0));
    }

    @Test
    void deliverToAlmostFullTank() throws Exception {
        double capacity = 100.0;
        mockPrivateField(tankClass, tank, "capacity", capacity);
        mockPrivateField(tankClass, tank, "storedLiters", 85.0);

        assertEquals(15.0, tank.deliver(30.0));
        assertEquals(capacity, tank.getStoredLiters());
    }


    /*  =========================
        Unit Tests für withdraw()
        =========================  */

    @Test
    void withdrawValidLiters() throws Exception {
        mockPrivateField(tankClass, tank, "capacity", 100.0);
        mockPrivateField(tankClass, tank, "storedLiters", 68.0);

        assertEquals(0.0, tank.withdraw(15.0));
        assertEquals(53.0, tank.getStoredLiters());
    }

    @Test
    void withdrawInvalidLiters() {
        assertEquals(0.0, tank.withdraw(-15.0));
    }

    @Test
    void withdrawToTankInMaintenance() throws Exception {
        mockPrivateField(tankClass, tank, "isUnderMaintenance", true);

        assertEquals(50.0, tank.withdraw(50.0));
    }

    @Test
    void deliverToEmptyTank() throws Exception {
        mockPrivateField(tankClass, tank, "capacity", 100.0);
        mockPrivateField(tankClass, tank, "storedLiters", 0.0);

        assertEquals(50.0, tank.withdraw(50.0));
    }

    @Test
    void deliverToAlmostEmptyTank() throws Exception {
        mockPrivateField(tankClass, tank, "capacity", 100.0);
        mockPrivateField(tankClass, tank, "storedLiters", 15.3);

        assertEquals(14.7, tank.withdraw(30.0));
        assertEquals(0.0, tank.getStoredLiters());
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