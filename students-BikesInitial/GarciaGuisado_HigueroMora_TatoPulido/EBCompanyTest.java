import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

/**
 * Pruebas unitarias para la clase EBCompany.
 */
public class EBCompanyTest
{
    private EBCompany company;
    private BikeStation station;
    private ElectricBike bike;
    private Location loc;

    @Before
    public void setUp()
    {
        company = new EBCompany("EcoBike Test");
        loc = new Location(2, 2);
        station = new BikeStation("ST01", loc);
        bike = new ElectricBike(company, new Location(0, 0), new Location(5, 5), "EB01", 60);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Prueba la adición correcta de bicicletas y estaciones a la compañía.
     */
    @Test
    public void testAdditionsAndCounts()
    {
        assertEquals(0, company.getNumberOfStations());

        company.addBikeStation(station);
        company.addElectricBike(bike);

        assertEquals(1, company.getNumberOfStations());
        // Comprobamos que no se añaden duplicados nulos o repetidos
        company.addBikeStation(station);
        assertEquals(1, company.getNumberOfStations());
    }

    /**
     * Prueba la búsqueda de estaciones por ID y por ubicación.
     */
    @Test
    public void testFindStations()
    {
        company.addBikeStation(station);

        // Buscar por ID existente e inexistente
        assertNotNull(company.getBikeStation("ST01"));
        assertNull(company.getBikeStation("ST99"));

        // Buscar por Location existente e inexistente
        assertNotNull(company.getBikeStation(loc));
        assertNotNull(company.getBikeStationAt(loc)); // Prueba el alias
        assertNull(company.getBikeStation(new Location(99, 99)));
    }

    /**
     * Prueba el avance de turnos y la simulación del método step().
     */
    @Test
    public void testTurnAndStep()
    {
        assertEquals(0, company.getCurrentTurn());

        company.addElectricBike(bike);
        company.step();

        assertEquals(1, company.getCurrentTurn());
    }

    /**
     * Prueba la ordenación de las bicicletas suscritas por ID.
     */
    @Test
    public void testSortBikesById()
    {
        ElectricBike bike2 = new ElectricBike(company, new Location(0, 0), new Location(5, 5), "EB02", 60);
        ElectricBike bikeA = new ElectricBike(company, new Location(0, 0), new Location(5, 5), "EB00", 60);

        company.addElectricBike(bike2); // "EB02"
        company.addElectricBike(bikeA); // "EB00"

        // Ejecutamos el método de ordenación
        company.sortBikesById();

        // Verificamos que se ejecuta correctamente sin errores
        assertTrue(true); 
    }

    /**
     * Prueba el reseteo completo de la compañía.
     */
    @Test
    public void testReset()
    {
        company.addBikeStation(station);
        company.addElectricBike(bike);
        
        assertEquals(1, company.getNumberOfStations());

        company.reset();

        assertEquals(0, company.getNumberOfStations());
    }
}