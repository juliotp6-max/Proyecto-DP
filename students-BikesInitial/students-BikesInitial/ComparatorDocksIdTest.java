import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase ComparatorDocksId.
 * Compara docks por su ID en orden creciente.
 * 
 * @author DP classes
 * @version 2026.10.13
 */
public class ComparatorDocksIdTest
{
    // Variables de instancia para el fixture de las pruebas
    private Dock dock1;
    private Dock dock2;
    private ComparatorDocksId comparator;

    /**
     * Configuración inicial (fixture) que se ejecuta antes de cada caso de prueba.
     */
    @Before
    public void setUp()
    {
        dock1 = new Dock("D01", 50, 0.10f);
        dock2 = new Dock("D02", 50, 0.10f);
        comparator = new ComparatorDocksId();
    }

    /**
     * Limpieza que se ejecuta después de cada caso de prueba.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testComparatorDocksId() {
        // "D01" es menor alfabéticamente que "D02", el resultado debe ser negativo (< 0)
        assertTrue(comparator.compare(dock1, dock2) < 0);
    }
}