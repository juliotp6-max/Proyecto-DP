import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase ComparatorBikeStationId.
 * Compara estaciones de bicicletas por su ID en orden creciente.
 * 
 * @author DP classes
 * @version 2026.10.13
 */
public class ComparatorBikeStationIdTest
{
    // Variables de instancia para el fixture de las pruebas
    private BikeStation station1;
    private BikeStation station2;
    private ComparatorBikeStationId comparator;

    /**
     * Configuración inicial (fixture) que se ejecuta antes de cada caso de prueba.
     */
    @Before
    public void setUp()
    {
        Location loc = new Location(0, 0);
        station1 = new BikeStation("ST01", loc);
        station2 = new BikeStation("ST02", loc);
        comparator = new ComparatorBikeStationId();
    }

    /**
     * Limpieza que se ejecuta después de cada caso de prueba.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testComparatorBikeStationId() {
        // "ST01" es menor alfabéticamente que "ST02", el resultado debe ser negativo (< 0)
        assertTrue(comparator.compare(station1, station2) < 0);
    }
}