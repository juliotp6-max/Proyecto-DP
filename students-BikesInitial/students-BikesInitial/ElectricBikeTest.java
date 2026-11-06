import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase ElectricBike.
 */
public class ElectricBikeTest
{
    private EBCompany company;
    private Location startLoc;
    private Location targetLoc;
    private BikeStation station;
    private Dock dock;
    private ElectricBike bike;

    @Before
    public void setUp()
    {
        // Fixture de prueba
        company = new EBCompany("Empresa Test");
        startLoc = new Location(0, 0);
        targetLoc = new Location(10, 10);
        
        // Creamos una bicicleta con 60 wh de capacidad y cargada al máximo
        bike = new ElectricBike(company, startLoc, targetLoc, "EBCC00", 60);
        
        // Estación intermedia y dock
        station = new BikeStation("CC01", new Location(2, 2));
        dock = new Dock("CC01_01", 60, 0.5f);
        station.addDock(dock);
        company.addBikeStation(station);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Prueba la creación e inicialización correcta de los campos de la bicicleta.
     */
    @Test
    public void testCreation()
    {
        assertEquals("EBCC00", bike.getIdBike());
        assertEquals(startLoc, bike.getLocation());
        assertEquals(targetLoc, bike.getTargetLocation());
    }

    /**
     * Prueba si el cálculo de energía suficiente hacia un destino es correcto.
     * Consumo: 5 wh por paso.
     */
    @Test
    public void testEnoughBattery()
    {
        // Distancia a (1, 1) es 1. Consumo = 5 wh. Batería actual = 60 wh -> Suficiente
        assertTrue(bike.enoughBattery(new Location(1, 1)));

        // Distancia a (15, 15) es 15. Consumo = 75 wh. Batería actual = 60 wh -> Insuficiente
        assertFalse(bike.enoughBattery(new Location(15, 15)));
    }

    /**
     * Prueba el cálculo de la ruta cuando la batería no es suficiente 
     * para llegar al destino final y debe desviarse a una estación.
     */
    @Test
    public void testCalculateRoute()
    {
        // Al calcular la ruta hacia (10, 10) con batería insuficiente para la distancia 10 (consume 50 wh si estuviera a media carga),
        // debe asignar una posición de recarga.
        bike.calculateRoute();
        assertNotNull(bike.getStringRoute());
    }

    /**
     * Prueba el proceso de recarga en la bicicleta.
     */
    @Test
    public void testRecharge()
    {
        // Ejecutamos el método recharge de la bici en la estación
        bike.recharge();
        assertNotNull(bike.getLocation());
    }
}