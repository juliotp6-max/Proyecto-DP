import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la clase ComparatorDockPriority utilizando JUnit 5.
 * Verifica que los docks se comparen correctamente según su prioridad 
 * (velocidad decreciente, tarifa de carga creciente e ID creciente).
 * 
 * @author DP classes
 * @version 2026.10.13
 */
public class ComparatorDockPriorityTest
{
    // Variables de instancia que formarán el fixture de las pruebas
    private Dock dock1;
    private Dock dock2;
    private ComparatorDockPriority comparator;

    /**
     * Configuración inicial (fixture) que se ejecuta antes de cada caso de prueba.
     */
    @BeforeEach
    public void setUp()
    {
        // Inicializamos los objetos comunes para los tests
        // Dock 1: velocidad alta (50), tarifa normal (0.10f), ID "D01"
        dock1 = new Dock("D01", 50, 0.10f);
        
        // Dock 2: velocidad baja (30), tarifa normal (0.10f), ID "D02"
        dock2 = new Dock("D02", 30, 0.10f);
        
        // Instanciamos el comparador a probar
        comparator = new ComparatorDockPriority();
    }

    /**
     * Limpieza que se ejecuta después de cada caso de prueba.
     */
    @AfterEach
    public void tearDown()
    {
        // Espacio para limpieza de recursos si fuera necesario
    }

    /**
     * Prueba el comparador de docks por prioridad basándose principalmente 
     * en la velocidad de carga en orden decreciente.
     */
    @Test
    public void testComparatorDockPriority() {
        // Como dock1 tiene mayor velocidad (orden decreciente), 
        // debe ir antes que dock2, por lo que el resultado de la comparación debe ser negativo (< 0)
        assertTrue(comparator.compare(dock1, dock2) < 0, 
                   "El dock con mayor velocidad de carga debería tener mayor prioridad (resultado negativo).");
    }
}