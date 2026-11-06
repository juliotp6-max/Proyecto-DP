import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase BikeStation.
 */
public class BikeStationTest
{
    private BikeStation station;

    @Before
    public void setUp()
    {
        // Fixture
        station = new BikeStation("CC01", new Location(5, 5));
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Comprueba que la operación para añadir un nuevo dock funciona 
     * y mantiene el listado actualizado.
     */
    @Test
    public void testAddDock()
    {
        assertEquals(0, station.getDocks().size());

        Dock d1 = new Dock("CC01_01", 40, 0.4f);
        Dock d2 = new Dock("CC01_02", 80, 0.8f);

        station.addDock(d1);
        station.addDock(d2);

        assertEquals(2, station.getDocks().size());
        // El dock más rápido (80wh) debe quedar primero tras ordenarse
        assertEquals("CC01_02", station.getDocks().get(0).getId());
    }
}