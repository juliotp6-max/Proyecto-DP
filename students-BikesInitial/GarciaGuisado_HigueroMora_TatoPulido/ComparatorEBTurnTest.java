import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase ComparatorEBTurn.
 * Compara bicicletas eléctricas por turno de llegada y desempatando por ID en orden creciente.
 * 
 * @author DP classes
 * @version 2026.10.13
 */
public class ComparatorEBTurnTest
{
    // Variables de instancia para el fixture de las pruebas
    private ElectricBike bike1;
    private ElectricBike bike2;
    private ComparatorEBTurn comparator;

    /**
     * Configuración inicial (fixture) que se ejecuta antes de cada caso de prueba.
     */
    @Before
    public void setUp()
    {
        EBCompany company = new EBCompany("TestCompany");
        Location loc = new Location(0, 0);
        
        bike1 = new ElectricBike(company, loc, loc, "EB01", 100);
        bike2 = new ElectricBike(company, loc, loc, "EB02", 100);
        comparator = new ComparatorEBTurn();
    }

    /**
     * Limpieza que se ejecuta después de cada caso de prueba.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testComparatorEBTurn() {
        // Al tener el mismo turno por defecto, desempatará por ID ("EB01" < "EB02")
        assertTrue(comparator.compare(bike1, bike2) < 0);
    }
}