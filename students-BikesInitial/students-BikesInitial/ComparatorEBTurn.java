import java.util.Comparator;

/**
 * Compares two ElectricBike objects based on their arrival turn in ascending order,
 * and in case of a tie, by their ID in ascending order.
 * 
 * @author DP classes
 * @version 2026.10.13
 */
public class ComparatorEBTurn implements Comparator<ElectricBike>
{
    public int compare(ElectricBike e1, ElectricBike e2){
        int turn1 = e1.getTurn();
        int turn2 = e2.getTurn();
        
        // 1. Orden creciente por turno de llegada
        if (turn1 != turn2) {
            return Integer.compare(turn1, turn2);
        }
        
        // 2. En caso de empate, orden creciente por ID de la bicicleta
        return e1.getIdBike().compareTo(e2.getIdBike());
    }
}