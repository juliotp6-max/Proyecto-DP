import java.util.*; 

/**
 * Compares two {@link Dock} objects based on their **ID** string in **ascending** order.
 * This is typically used as a final tie-breaker in multi-criteria sorting.
 * @author DP Clasess
 * @version 2026.10.13
 */
public class ComparatorDocksId implements Comparator<Dock>
{
    /**
     * Compares its two {@link Dock} arguments for order.
     * The comparison is based on the lexicographical order of their IDs.
     * @param c1 The first dock to be compared.
     * @param c2 The second dock to be compared.
     * @return A negative integer, zero, or a positive integer as the first 
     * dock's ID is lexicographically less than, equal to, or greater than the second.
     */
    public int compare(Dock c1, Dock c2){  
        //TODO: Complete this code   
        return 0;  
    } 
}
