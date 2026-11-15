import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase ComparatorBikeStationBikes.
 * Compara estaciones por número total de bicicletas recargadas (decreciente) y desempatando por ID.
 * 
 * @author DP classes
 * @version 2026.10.13
 */
public class ComparatorBikeStationBikesTest
{
    // Variables de instancia para el fixture de las pruebas
    private BikeStation station1;
    private BikeStation station2;
    private ComparatorBikeStationBikes comparator;

    /**
     * Configuración inicial (fixture) que se ejecuta antes de cada caso de prueba.
     */
    @Before
    public void setUp()
    {
        Location loc = new Location(0, 0);
        station1 = new BikeStation("ST01", loc);
        station2 = new BikeStation("ST02", loc);
        comparator = new ComparatorBikeStationBikes();
    }

    /**
     * Limpieza que se ejecuta después de cada caso de prueba.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testComparatorBikeStationBikes() {
        // Al tener ambas 0 bicis recargadas, desempatará por ID ("ST01" < "ST02")
        assertTrue(comparator.compare(station1, station2) < 0);
    }
}