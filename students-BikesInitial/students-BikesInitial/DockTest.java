import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase Dock.
 */
public class DockTest
{
    private Dock dock;
    private EBCompany company;
    private ElectricBike bike;

    @Before
    public void setUp()
    {
        // Fixture: creación de objetos base para los tests
        dock = new Dock("CC01_01", 60, 0.5f);
        company = new EBCompany("Test Company");
        bike = new ElectricBike(company, new Location(0, 0), new Location(5, 5), "EB01", 60);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Prueba el método recharge, verificando el cálculo del coste, 
     * la actualización de lo recaudado y el registro de la bicicleta recargada.
     */
    @Test
    public void testRecharge()
    {
        // Estado inicial de la dársena
        assertEquals(0, dock.getNumerEVRecharged());
        assertEquals(0.0f, dock.getAmountCollected(), 0.001f);

        // Realizamos una recarga de 40 wh (40 wh * 0.5 €/wh = 20.0 €)
        float cost = dock.recharge(bike, 40);

        // Verificaciones
        assertEquals(20.0f, cost, 0.001f);
        assertEquals(20.0f, dock.getAmountCollected(), 0.001f);
        assertEquals(1, dock.getNumerEVRecharged());
    }
}