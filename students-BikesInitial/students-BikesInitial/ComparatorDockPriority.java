import java.util.Comparator;

/**
 * Compara dos objetos {@link Dock} basándose en su velocidad de carga en orden decreciente,
 * en caso de empate por su tarifa de carga en orden creciente,
 * y si hay otro empate, por su ID en orden creciente.
 * 
 * @author DP classes
 * @version 2026.10.13
 * @param d1 El primer dock a comparar.
 * @param d2 El segundo dock a comparar.
 * @return Un entero negativo, cero o un entero positivo según el criterio de ordenación.
 */
public class ComparatorDockPriority implements Comparator<Dock>
{
    public int compare(Dock d1, Dock d2) {
        // 1. Orden decreciente por velocidad de carga (d2 comparado con d1)
        int speedCmp = Integer.compare(d2.getChargingSpeed(), d1.getChargingSpeed());
        if (speedCmp != 0) {
            return speedCmp;
        }
        
        // 2. En caso de empate, orden creciente por tarifa de carga
        int feeCmp = Float.compare(d1.getChargingFee(), d2.getChargingFee());
        if (feeCmp != 0) {
            return feeCmp;
        }
        
        // 3. Si persiste el empate, orden creciente por ID del dock
        return d1.getId().compareTo(d2.getId());
    }
}