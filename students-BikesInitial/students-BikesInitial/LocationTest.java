import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link Location} class.
 * Provides unit tests for methods like {@code distance()} and {@code nextLocation()}.
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 * @version 2026.10.13 DP classes (adaptado a Java 8+)
 */
public class LocationTest
{
    /**
     * Default constructor for test class LocationTest.
     */
    public LocationTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // No setup required for Location tests
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // No teardown required for Location tests
    }
    
    /**
     * Test the {@code distance} method of the {@link Location} class.
     * Checks distances from a central location to surrounding points.
     */
    @Test
    public void testDistance() {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 5);
        assertEquals(5, loc1.distance(loc2));
        Location p2 = new Location(6, 6);
        Location p3 = new Location(5, 2);
        assertEquals(4, p2.distance(p3));
    }
    
    /**
     * Test the {@code nextLocation} method when the destination is adjacent 
     * (one step away in any direction).
     */
    @Test
    public void testAdjacentLocations() {
        Location current = new Location(3, 3);
        Location target = new Location(4, 4);
        Location next = current.nextLocation(target);
        assertEquals(new Location(4, 4), next);
    }
    
    /**
     * Test the {@code nextLocation} method when the destination is not adjacent 
     * (more than one step away).
     */
    @Test
    public void testNonAdjacentLocations() {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 5);
        Location next = loc1.nextLocation(loc2);
        assertEquals(new Location(1, 1), next);
    }
}